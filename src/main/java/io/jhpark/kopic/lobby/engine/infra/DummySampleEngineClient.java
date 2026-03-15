package io.jhpark.kopic.lobby.engine.infra;

import io.jhpark.kopic.lobby.engine.app.EngineMigrationPort;
import io.jhpark.kopic.lobby.engine.app.EnginePrivateRoomPort;
import io.jhpark.kopic.lobby.engine.app.EngineQuickJoinPort;
import io.jhpark.kopic.lobby.engine.app.EngineRandomRoomPort;
import io.jhpark.kopic.lobby.engine.domain.CreatePrivateRoomCommand;
import io.jhpark.kopic.lobby.engine.domain.CreateRandomRoomCommand;
import io.jhpark.kopic.lobby.engine.domain.JoinUserCommand;
import io.jhpark.kopic.lobby.engine.domain.PrivateRoomCreated;
import io.jhpark.kopic.lobby.engine.domain.RandomRoomCreated;
import io.jhpark.kopic.lobby.matchmaking.domain.QuickJoinResult;
import io.jhpark.kopic.lobby.migration.domain.MigrationFailureReason;
import io.jhpark.kopic.lobby.migration.domain.MigrationPayload;
import io.jhpark.kopic.lobby.migration.domain.MigrationResult;
import org.springframework.stereotype.Component;

/**
 * Dummy sample GE client.
 * Replace this class with a real HTTP/gRPC client later.
 */
@Component
public class DummySampleEngineClient implements
        EnginePrivateRoomPort,
        EngineRandomRoomPort,
        EngineQuickJoinPort,
        EngineMigrationPort {

    private final DummySampleLobbyStateStore stateStore;

    public DummySampleEngineClient(DummySampleLobbyStateStore stateStore) {
        this.stateStore = stateStore;
    }

    @Override
    public PrivateRoomCreated createPrivateRoom(String engineId, CreatePrivateRoomCommand command) {
        return new PrivateRoomCreated(stateStore.createPrivateRoom(engineId));
    }

    @Override
    public RandomRoomCreated createRandomRoom(String engineId, CreateRandomRoomCommand command) {
        return new RandomRoomCreated(stateStore.createRandomRoom(engineId));
    }

    @Override
    public QuickJoinResult tryJoinRandomRoom(String engineId, String roomId, JoinUserCommand command) {
        return stateStore.tryJoinRandomRoom(engineId, roomId)
                .map(roomEntry -> new QuickJoinResult(true, false, roomEntry))
                .orElseGet(() -> new QuickJoinResult(false, false, null));
    }

    @Override
    public MigrationResult importWaitingRoom(String targetEngineId, MigrationPayload payload) {
        return new MigrationResult(true, null);
    }
}
