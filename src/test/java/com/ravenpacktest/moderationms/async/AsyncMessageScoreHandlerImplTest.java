package com.ravenpacktest.moderationms.async;


import com.ravenpacktest.moderationms.dto.UserMessageDto;
import com.ravenpacktest.moderationms.dto.UserMessageScoreDto;
import com.ravenpacktest.moderationms.client.scoring.ScoringServiceProvider;
import com.ravenpacktest.moderationms.client.translation.TranslationServiceProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AsyncMessageScoreHandlerImplTest {


    public static final String WILLAKUY = "willakuy"; //Message in Quichua
    public static final String MESSAGE = "message";
    @Mock
    private ScoringServiceProvider scoringServiceProvider;

    @Mock
    private TranslationServiceProvider translationServiceProvider;

    @InjectMocks
    private AsyncMessageScoreHandlerImpl messageScoreHandler;

    @Test
    void testMessageScorePassedCorrectlyInCaseOfNoTranslation() throws ExecutionException, InterruptedException {
        when(translationServiceProvider.getTranslatedMessage(MESSAGE)).thenReturn(MESSAGE);
        when(scoringServiceProvider.getOffensivenessScore(MESSAGE)).thenReturn(1f);
        CompletableFuture<UserMessageScoreDto> message = messageScoreHandler.calculateMessageScore(new UserMessageDto("1", MESSAGE));
        CompletableFuture<UserMessageScoreDto> expected = CompletableFuture.completedFuture(new UserMessageScoreDto("1", MESSAGE, 1f));
        assertEquals(expected.get(), message.get());
    }

    @Test
    void testMessageScorePassedCorrectlyInCaseOfTranslation() throws ExecutionException, InterruptedException {
        when(translationServiceProvider.getTranslatedMessage(WILLAKUY)).thenReturn(MESSAGE);
        when(scoringServiceProvider.getOffensivenessScore(MESSAGE)).thenReturn(1f);
        CompletableFuture<UserMessageScoreDto> message = messageScoreHandler.calculateMessageScore(new UserMessageDto("1", WILLAKUY));
        CompletableFuture<UserMessageScoreDto> expected = CompletableFuture.completedFuture(new UserMessageScoreDto("1", MESSAGE, 1f));
        assertEquals(expected.get(), message.get());
    }

    @Test
    void testTranslationServiceProviderWasCalledOneTime() throws ExecutionException, InterruptedException {
        when(translationServiceProvider.getTranslatedMessage(WILLAKUY)).thenReturn(MESSAGE);
        when(scoringServiceProvider.getOffensivenessScore(MESSAGE)).thenReturn(1f);
        UserMessageScoreDto messageScoreDto = messageScoreHandler.calculateMessageScore(new UserMessageDto("1", WILLAKUY)).get();
        verify(translationServiceProvider, times(1)).getTranslatedMessage(WILLAKUY);
    }

    @Test
    void testScoringnServiceProviderWasCalledOneTime() throws ExecutionException, InterruptedException {
        when(translationServiceProvider.getTranslatedMessage(WILLAKUY)).thenReturn(MESSAGE);
        when(scoringServiceProvider.getOffensivenessScore(MESSAGE)).thenReturn(0.1f);
        UserMessageScoreDto messageScoreDto = messageScoreHandler.calculateMessageScore(new UserMessageDto("1", WILLAKUY)).get();
        verify(scoringServiceProvider, times(1)).getOffensivenessScore(MESSAGE);
    }

}