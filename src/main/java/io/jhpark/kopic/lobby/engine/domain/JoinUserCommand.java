package io.jhpark.kopic.lobby.engine.domain;

public record JoinUserCommand(
        String userId,
        String name
) {
}
