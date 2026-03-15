package io.jhpark.kopic.lobby.room.dto;

public record CreatePrivateRoomRequest(
        String userId,
        String name
) {
}
