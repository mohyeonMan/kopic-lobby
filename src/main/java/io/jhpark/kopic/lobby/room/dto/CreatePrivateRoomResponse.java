package io.jhpark.kopic.lobby.room.dto;

import io.jhpark.kopic.lobby.room.domain.RoomEntry;
import io.jhpark.kopic.lobby.room.domain.RoomType;

public record CreatePrivateRoomResponse(
        String roomId,
        RoomType roomType,
        String roomCode,
        RoomEntryResponse entry
) {

    public static CreatePrivateRoomResponse from(RoomEntry roomEntry) {
        return new CreatePrivateRoomResponse(
                roomEntry.roomId(),
                roomEntry.roomType(),
                roomEntry.roomCode(),
                RoomEntryResponse.from(roomEntry)
        );
    }
}
