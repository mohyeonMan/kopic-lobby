package io.jhpark.kopic.lobby.engine.app;

import io.jhpark.kopic.lobby.engine.domain.CreatePrivateRoomCommand;
import io.jhpark.kopic.lobby.engine.domain.PrivateRoomCreated;

public interface EnginePrivateRoomPort {

    PrivateRoomCreated createPrivateRoom(String engineId, CreatePrivateRoomCommand command);
}
