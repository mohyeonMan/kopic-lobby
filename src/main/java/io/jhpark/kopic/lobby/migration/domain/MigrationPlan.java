package io.jhpark.kopic.lobby.migration.domain;

public record MigrationPlan(
        String roomId,
        String sourceEngineId,
        String targetEngineId,
        String targetEngineEndpoint
) {
}
