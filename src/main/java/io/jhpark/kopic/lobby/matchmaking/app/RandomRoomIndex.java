package io.jhpark.kopic.lobby.matchmaking.app;

import io.jhpark.kopic.lobby.matchmaking.domain.QuickJoinCandidate;
import java.util.List;

public interface RandomRoomIndex {

    List<QuickJoinCandidate> findJoinableCandidates(int limit);
}
