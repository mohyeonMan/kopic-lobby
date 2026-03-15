package io.jhpark.kopic.lobby.migration.app;

import io.jhpark.kopic.lobby.allocation.app.EngineAllocationService;
import io.jhpark.kopic.lobby.directory.app.RoomOwnershipUpdater;
import io.jhpark.kopic.lobby.directory.app.RoomRoutingReader;
import io.jhpark.kopic.lobby.directory.domain.RoomOwnership;
import io.jhpark.kopic.lobby.engine.app.EngineMigrationPort;
import io.jhpark.kopic.lobby.migration.domain.MigrationPayload;
import io.jhpark.kopic.lobby.migration.domain.MigrationResult;
import io.jhpark.kopic.lobby.migration.domain.MigrationState;
import io.jhpark.kopic.lobby.migration.domain.MigrationStatus;
import io.jhpark.kopic.lobby.migration.dto.ReassignRoomRequest;
import io.jhpark.kopic.lobby.migration.infra.DummySampleMigrationStateStore;
import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RoomMigrationCoordinator {

    private final EngineAllocationService engineAllocationService;
    private final RoomRoutingReader roomRoutingReader;
    private final RoomOwnershipUpdater roomOwnershipUpdater;
    private final EngineMigrationPort engineMigrationPort;
    private final DummySampleMigrationStateStore migrationStateStore;

    public RoomMigrationCoordinator(
            EngineAllocationService engineAllocationService,
            RoomRoutingReader roomRoutingReader,
            RoomOwnershipUpdater roomOwnershipUpdater,
            EngineMigrationPort engineMigrationPort,
            DummySampleMigrationStateStore migrationStateStore
    ) {
        this.engineAllocationService = engineAllocationService;
        this.roomRoutingReader = roomRoutingReader;
        this.roomOwnershipUpdater = roomOwnershipUpdater;
        this.engineMigrationPort = engineMigrationPort;
        this.migrationStateStore = migrationStateStore;
    }

    public MigrationStatus reassign(String roomId, ReassignRoomRequest request) {
        String currentOwner = roomRoutingReader.findByRoomId(roomId)
                .map(RoomOwnership::ownerEngineId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "room not found"));

        if (!currentOwner.equals(request.sourceEngineId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "room reassignment not allowed");
        }

        MigrationStatus requested = new MigrationStatus(
                roomId,
                MigrationState.REQUESTED,
                request.sourceEngineId(),
                null,
                Instant.now()
        );
        migrationStateStore.save(requested);

        String targetEngineId = engineAllocationService.allocateOwnerEngineIdExcluding(request.sourceEngineId());
        migrationStateStore.save(new MigrationStatus(
                roomId,
                MigrationState.IMPORTING,
                request.sourceEngineId(),
                targetEngineId,
                Instant.now()
        ));

        MigrationResult result = engineMigrationPort.importWaitingRoom(
                targetEngineId,
                new MigrationPayload(
                        roomId,
                        request.sourceEngineId(),
                        new MigrationPayload.RoomSettings(
                                request.settings().roundCount(),
                                request.settings().drawSec(),
                                request.settings().wordChoiceCount(),
                                request.settings().endMode()
                        )
                )
        );

        MigrationStatus finalStatus;
        if (result.success()) {
            roomOwnershipUpdater.update(new RoomOwnership(roomId, targetEngineId));
            finalStatus = new MigrationStatus(
                    roomId,
                    MigrationState.COMPLETED,
                    request.sourceEngineId(),
                    targetEngineId,
                    Instant.now()
            );
        } else {
            finalStatus = new MigrationStatus(
                    roomId,
                    MigrationState.FAILED,
                    request.sourceEngineId(),
                    targetEngineId,
                    Instant.now()
            );
        }
        migrationStateStore.save(finalStatus);
        return finalStatus;
    }

    public MigrationStatus getStatus(String roomId) {
        return migrationStateStore.findByRoomId(roomId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "room not found"));
    }
}
