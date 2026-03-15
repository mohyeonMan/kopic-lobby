package io.jhpark.kopic.lobby.migration.domain;

public record MigrationResult(
        boolean success,
        MigrationFailureReason failureReason
) {
}
