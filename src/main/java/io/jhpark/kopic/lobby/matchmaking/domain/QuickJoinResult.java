package io.jhpark.kopic.lobby.matchmaking.domain;

import io.jhpark.kopic.lobby.room.domain.RoomEntry;

public record QuickJoinResult(
        boolean joined,
        boolean created,
        RoomEntry roomEntry
) {
}
