package io.jhpark.kopic.lobby.engine.app;

import io.jhpark.kopic.lobby.engine.domain.JoinUserCommand;
import io.jhpark.kopic.lobby.matchmaking.domain.QuickJoinResult;

public interface EngineQuickJoinPort {

    QuickJoinResult tryJoinRandomRoom(String engineId, String roomId, JoinUserCommand command);
}
