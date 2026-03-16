package io.jhpark.kopic.lobby.migration.app;

import io.jhpark.kopic.lobby.allocation.app.EngineAllocationService;
import io.jhpark.kopic.lobby.directory.app.RoomRoutingReader;
import io.jhpark.kopic.lobby.directory.domain.RoomOwnership;
import io.jhpark.kopic.lobby.engine.domain.EnginePresence;
import io.jhpark.kopic.lobby.migration.domain.MigrationPlan;
import io.jhpark.kopic.lobby.migration.dto.ReassignRoomRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RoomMigrationCoordinator {

    private final EngineAllocationService engineAllocationService;
    private final RoomRoutingReader roomRoutingReader;

    public RoomMigrationCoordinator(
            EngineAllocationService engineAllocationService,
            RoomRoutingReader roomRoutingReader
    ) {
        this.engineAllocationService = engineAllocationService;
        this.roomRoutingReader = roomRoutingReader;
    }

    public MigrationPlan reassign(String roomId, ReassignRoomRequest request) {
        String currentOwner = roomRoutingReader.findByRoomId(roomId)
                .map(RoomOwnership::ownerEngineId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "room not found"));

        if (!currentOwner.equals(request.sourceEngineId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "room reassignment not allowed");
        }

        EnginePresence target = engineAllocationService.allocateOwnerEngineExcluding(request.sourceEngineId());
        return new MigrationPlan(
                roomId,
                request.sourceEngineId(),
                target.engineId(),
                target.endpoint()
        );
    }
}
