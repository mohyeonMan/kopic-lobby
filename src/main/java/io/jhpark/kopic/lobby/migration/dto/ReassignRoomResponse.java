package io.jhpark.kopic.lobby.migration.dto;

import io.jhpark.kopic.lobby.migration.domain.MigrationStatus;

public record ReassignRoomResponse(
        String roomId,
        String sourceEngineId,
        String targetEngineId,
        boolean reassigned
) {

    public static ReassignRoomResponse from(MigrationStatus status) {
        return new ReassignRoomResponse(
                status.roomId(),
                status.sourceEngineId(),
                status.targetEngineId(),
                status.state() == io.jhpark.kopic.lobby.migration.domain.MigrationState.COMPLETED
        );
    }
}
