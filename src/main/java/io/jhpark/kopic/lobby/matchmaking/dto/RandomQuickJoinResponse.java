package io.jhpark.kopic.lobby.matchmaking.dto;

import io.jhpark.kopic.lobby.matchmaking.domain.QuickJoinResult;
import io.jhpark.kopic.lobby.room.dto.RoomEntryResponse;
import io.jhpark.kopic.lobby.room.domain.RoomEntry;
import io.jhpark.kopic.lobby.room.domain.RoomType;

public record RandomQuickJoinResponse(
        boolean matched,
        boolean created,
        String roomId,
        RoomType roomType,
        RoomEntryResponse entry
) {

    public static RandomQuickJoinResponse from(QuickJoinResult result) {
        RoomEntry roomEntry = result.roomEntry();
        return new RandomQuickJoinResponse(
                result.joined(),
                result.created(),
                roomEntry.roomId(),
                roomEntry.roomType(),
                RoomEntryResponse.from(roomEntry)
        );
    }
}
