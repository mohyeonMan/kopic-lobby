package io.jhpark.kopic.lobby.migration.domain;

public record MigrationRequest(
        String roomId,
        String sourceEngineId
) {
}
