package io.jhpark.kopic.lobby.directory.domain;

public record RoomOwnership(
        String roomId,
        String ownerEngineId
) {
}
