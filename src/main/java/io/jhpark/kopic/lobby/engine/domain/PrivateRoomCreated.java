package io.jhpark.kopic.lobby.engine.domain;

import io.jhpark.kopic.lobby.room.domain.RoomEntry;

public record PrivateRoomCreated(
        RoomEntry roomEntry
) {
}
