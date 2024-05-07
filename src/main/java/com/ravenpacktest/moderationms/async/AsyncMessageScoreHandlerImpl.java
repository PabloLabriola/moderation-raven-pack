package com.ravenpacktest.moderationms.async;


import com.ravenpacktest.moderationms.dto.UserMessageDto;
import com.ravenpacktest.moderationms.dto.UserMessageScoreDto;
import com.ravenpacktest.moderationms.client.scoring.ScoringServiceProvider;
import com.ravenpacktest.moderationms.client.translation.TranslationServiceProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class AsyncMessageScoreHandlerImpl implements AsyncMessageScoreHandler {

    private final ScoringServiceProvider scoringServiceProvider;
    private final TranslationServiceProvider translationServiceProvider;

    @Async
    @Override
    public CompletableFuture<UserMessageScoreDto> calculateMessageScore(final UserMessageDto userMessageDto) {
        return CompletableFuture.supplyAsync(() -> {
            final String translatedMessage = translationServiceProvider.getTranslatedMessage(userMessageDto.message());
            float offensivenessScore = scoringServiceProvider.getOffensivenessScore(translatedMessage);
            return new UserMessageScoreDto(userMessageDto.userId(), translatedMessage, offensivenessScore);
        });
    }
}
