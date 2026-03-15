package io.jhpark.kopic.lobby.room.dto;

import io.jhpark.kopic.lobby.room.domain.RoomEntry;
import io.jhpark.kopic.lobby.room.domain.RoomType;

public record RoomEntryResponse(
        String roomId,
        RoomType roomType,
        WsEntryResponse ws
) {

    public static RoomEntryResponse from(RoomEntry roomEntry) {
        return new RoomEntryResponse(
                roomEntry.roomId(),
                roomEntry.roomType(),
                new WsEntryResponse(roomEntry.roomId())
        );
    }

    public record WsEntryResponse(String roomId) {
    }
}
