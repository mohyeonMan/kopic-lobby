package io.jhpark.kopic.lobby.migration.dto;

import io.jhpark.kopic.lobby.migration.domain.MigrationPlan;

public record ReassignRoomResponse(
        String roomId,
        String sourceEngineId,
        String targetEngineId,
        String targetEngineEndpoint,
        boolean reassigned
) {

    public static ReassignRoomResponse from(MigrationPlan plan) {
        return new ReassignRoomResponse(
                plan.roomId(),
                plan.sourceEngineId(),
                plan.targetEngineId(),
                plan.targetEngineEndpoint(),
                true
        );
    }
}
