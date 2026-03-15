package io.jhpark.kopic.lobby.engine.app;

import io.jhpark.kopic.lobby.migration.domain.MigrationPayload;
import io.jhpark.kopic.lobby.migration.domain.MigrationResult;

public interface EngineMigrationPort {

    MigrationResult importWaitingRoom(String targetEngineId, MigrationPayload payload);
}
