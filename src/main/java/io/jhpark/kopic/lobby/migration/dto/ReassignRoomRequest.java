package io.jhpark.kopic.lobby.migration.dto;

public record ReassignRoomRequest(
        String sourceEngineId,
        String roomStatus,
        String reason,
        Settings settings,
        String requestedAt
) {

    public record Settings(
            int roundCount,
            int drawSec,
            int wordChoiceCount,
            String endMode
    ) {
    }
}
