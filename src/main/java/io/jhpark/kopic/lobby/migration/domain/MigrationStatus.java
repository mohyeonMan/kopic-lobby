package io.jhpark.kopic.lobby.migration.domain;

import java.time.Instant;

public record MigrationStatus(
        String roomId,
        MigrationState state,
        String sourceEngineId,
        String targetEngineId,
        Instant updatedAt
) {
}
