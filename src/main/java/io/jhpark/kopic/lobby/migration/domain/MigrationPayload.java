package io.jhpark.kopic.lobby.migration.domain;

public record MigrationPayload(
        String roomId,
        String sourceEngineId,
        RoomSettings settings
) {

    public record RoomSettings(
            int roundCount,
            int drawSec,
            int wordChoiceCount,
            String endMode
    ) {
    }
}
