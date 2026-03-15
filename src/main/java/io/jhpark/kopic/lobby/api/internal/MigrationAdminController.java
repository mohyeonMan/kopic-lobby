package io.jhpark.kopic.lobby.api.internal;

import io.jhpark.kopic.lobby.migration.app.RoomMigrationCoordinator;
import io.jhpark.kopic.lobby.migration.dto.MigrationStatusResponse;
import io.jhpark.kopic.lobby.migration.dto.ReassignRoomRequest;
import io.jhpark.kopic.lobby.migration.dto.ReassignRoomResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/lobby/migrations/rooms")
public class MigrationAdminController {

    private final RoomMigrationCoordinator roomMigrationCoordinator;

    public MigrationAdminController(RoomMigrationCoordinator roomMigrationCoordinator) {
        this.roomMigrationCoordinator = roomMigrationCoordinator;
    }

    @PostMapping("/{roomId}/reassign")
    public ReassignRoomResponse reassign(
            @PathVariable String roomId,
            @RequestBody ReassignRoomRequest request
    ) {
        return ReassignRoomResponse.from(roomMigrationCoordinator.reassign(roomId, request));
    }

    @GetMapping("/{roomId}")
    public MigrationStatusResponse getStatus(@PathVariable String roomId) {
        return MigrationStatusResponse.from(roomMigrationCoordinator.getStatus(roomId));
    }
}
