package io.jhpark.kopic.lobby.matchmaking.app;

import io.jhpark.kopic.lobby.allocation.app.EngineAllocationService;
import io.jhpark.kopic.lobby.directory.app.RoomRoutingReader;
import io.jhpark.kopic.lobby.engine.app.EngineQuickJoinPort;
import io.jhpark.kopic.lobby.engine.app.EngineRandomRoomPort;
import io.jhpark.kopic.lobby.engine.domain.CreateRandomRoomCommand;
import io.jhpark.kopic.lobby.engine.domain.JoinUserCommand;
import io.jhpark.kopic.lobby.matchmaking.domain.QuickJoinCandidate;
import io.jhpark.kopic.lobby.matchmaking.domain.QuickJoinResult;
import io.jhpark.kopic.lobby.matchmaking.dto.RandomQuickJoinRequest;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RandomQuickJoinService {

    private static final int DEFAULT_CANDIDATE_LIMIT = 10;

    private final RandomRoomIndex randomRoomIndex;
    private final RoomRoutingReader roomRoutingReader;
    private final EngineQuickJoinPort engineQuickJoinPort;
    private final EngineRandomRoomPort engineRandomRoomPort;
    private final EngineAllocationService engineAllocationService;

    public RandomQuickJoinService(
            RandomRoomIndex randomRoomIndex,
            RoomRoutingReader roomRoutingReader,
            EngineQuickJoinPort engineQuickJoinPort,
            EngineRandomRoomPort engineRandomRoomPort,
            EngineAllocationService engineAllocationService
    ) {
        this.randomRoomIndex = randomRoomIndex;
        this.roomRoutingReader = roomRoutingReader;
        this.engineQuickJoinPort = engineQuickJoinPort;
        this.engineRandomRoomPort = engineRandomRoomPort;
        this.engineAllocationService = engineAllocationService;
    }

    public QuickJoinResult quickJoin(RandomQuickJoinRequest request) {
        List<QuickJoinCandidate> candidates = randomRoomIndex.findJoinableCandidates(DEFAULT_CANDIDATE_LIMIT);

        for (QuickJoinCandidate candidate : candidates) {
            String ownerEngineId = roomRoutingReader.findByRoomId(candidate.roomId())
                    .map(ownership -> ownership.ownerEngineId())
                    .orElse(null);

            if (ownerEngineId == null) {
                continue;
            }

            QuickJoinResult result = engineQuickJoinPort.tryJoinRandomRoom(
                    ownerEngineId,
                    candidate.roomId(),
                    new JoinUserCommand(request.userId(), request.name())
            );

            if (result.joined()) {
                return result;
            }
        }

        String engineId = engineAllocationService.allocateOwnerEngineId();
        return new QuickJoinResult(
                true,
                true,
                engineRandomRoomPort.createRandomRoom(
                        engineId,
                        new CreateRandomRoomCommand(request.userId(), request.name())
                ).roomEntry()
        );
    }
}
