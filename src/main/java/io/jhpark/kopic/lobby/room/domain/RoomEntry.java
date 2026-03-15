package io.jhpark.kopic.lobby.room.domain;

public record RoomEntry(
        String roomId,
        RoomType roomType,
        String roomCode,
        String ownerEngineId
) {
}
