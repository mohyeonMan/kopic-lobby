package io.jhpark.kopic.lobby.room.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RoomType {
    PRIVATE,
    RANDOM;

    @JsonValue
    public String jsonValue() {
        return name().toLowerCase();
    }
}
