package io.jhpark.kopic.lobby.engine.domain;

public record CreatePrivateRoomCommand(
        String userId,
        String name
) {
}
