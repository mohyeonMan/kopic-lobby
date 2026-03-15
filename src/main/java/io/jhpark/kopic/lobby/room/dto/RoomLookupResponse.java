package io.jhpark.kopic.lobby.room.dto;

import io.jhpark.kopic.lobby.room.domain.RoomEntry;
import io.jhpark.kopic.lobby.room.domain.RoomType;

public record RoomLookupResponse(
        String roomId,
        RoomType roomType,
        String roomCode,
        RoomEntryResponse entry
) {

    public static RoomLookupResponse from(RoomEntry roomEntry) {
        return new RoomLookupResponse(
                roomEntry.roomId(),
                roomEntry.roomType(),
                roomEntry.roomCode(),
                RoomEntryResponse.from(roomEntry)
        );
    }
}
