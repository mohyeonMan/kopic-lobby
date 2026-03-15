package io.jhpark.kopic.lobby.api.publicapi;

import io.jhpark.kopic.lobby.matchmaking.app.RandomQuickJoinService;
import io.jhpark.kopic.lobby.matchmaking.dto.RandomQuickJoinRequest;
import io.jhpark.kopic.lobby.matchmaking.dto.RandomQuickJoinResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lobby/matchmaking")
public class MatchmakingController {

    private final RandomQuickJoinService randomQuickJoinService;

    public MatchmakingController(RandomQuickJoinService randomQuickJoinService) {
        this.randomQuickJoinService = randomQuickJoinService;
    }

    @PostMapping("/random")
    public RandomQuickJoinResponse randomQuickJoin(@RequestBody RandomQuickJoinRequest request) {
        return RandomQuickJoinResponse.from(randomQuickJoinService.quickJoin(request));
    }
}
