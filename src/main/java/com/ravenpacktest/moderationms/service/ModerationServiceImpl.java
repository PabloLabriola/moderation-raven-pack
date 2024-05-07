package com.ravenpacktest.moderationms.service;


import com.ravenpacktest.moderationms.async.AsyncMessageScoreHandler;
import com.ravenpacktest.moderationms.dto.ModerationResultDto;
import com.ravenpacktest.moderationms.dto.UserMessageDto;
import com.ravenpacktest.moderationms.dto.UserMessageScoreDto;
import com.ravenpacktest.moderationms.exception.ModerationServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
public class ModerationServiceImpl implements ModerationService {

    private final AsyncMessageScoreHandler asyncMessageScoreHandler;

    public List<ModerationResultDto> calculateModerationResults(final List<UserMessageDto> usersMessages) {
        final List<UserMessageScoreDto> userScores = usersMessages.parallelStream()
                                                                  .map(asyncMessageScoreHandler::calculateMessageScore)
                                                                  .map(CompletableFuture::join)
                                                                  .filter(Objects::nonNull)
                                                                  .toList();
        return getModeratingResults(userScores);
    }

    private List<ModerationResultDto> getModeratingResults(final List<UserMessageScoreDto> scores) {
        final Map<String, List<UserMessageScoreDto>> userIdToMessageScoresMap = scores.stream()
                                                                                      .collect(groupingBy(UserMessageScoreDto::userId));
        return userIdToMessageScoresMap.entrySet()
                                       .stream()
                                       .map(userIdScoresEntry -> getModerationResult(userIdScoresEntry.getKey(), userIdScoresEntry.getValue()))
                                       .toList();
    }

    private static ModerationResultDto getModerationResult(final String userId, final List<UserMessageScoreDto> scores) {
        final long messagesAmount = scores.size();
        final Double averageScore = scores.stream()
                                          .mapToDouble(UserMessageScoreDto::score)
                                          .average()
                                          .orElseThrow(() -> new ModerationServerException("No offensive score found for user %s".formatted(userId)));
        return new ModerationResultDto(userId, messagesAmount, averageScore.floatValue());
    }
}
