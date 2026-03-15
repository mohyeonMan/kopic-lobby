package io.jhpark.kopic.lobby.engine.app;

import io.jhpark.kopic.lobby.engine.domain.CreateRandomRoomCommand;
import io.jhpark.kopic.lobby.engine.domain.RandomRoomCreated;

public interface EngineRandomRoomPort {

    RandomRoomCreated createRandomRoom(String engineId, CreateRandomRoomCommand command);
}
