package io.jhpark.kopic.lobby.migration.dto;

import io.jhpark.kopic.lobby.migration.domain.MigrationStatus;

public record MigrationStatusResponse(
        String roomId,
        String state,
        String sourceEngineId,
        String targetEngineId,
        String updatedAt
) {

    public static MigrationStatusResponse from(MigrationStatus status) {
        return new MigrationStatusResponse(
                status.roomId(),
                status.state().name(),
                status.sourceEngineId(),
                status.targetEngineId(),
                status.updatedAt().toString()
        );
    }
}
