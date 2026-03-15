package io.jhpark.kopic.lobby.engine.domain;

public record CreateRandomRoomCommand(
        String userId,
        String name
) {
}
