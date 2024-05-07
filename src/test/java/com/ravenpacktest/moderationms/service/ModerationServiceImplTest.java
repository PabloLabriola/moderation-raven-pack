package com.ravenpacktest.moderationms.service;

import com.ravenpacktest.moderationms.async.AsyncMessageScoreHandler;
import com.ravenpacktest.moderationms.dto.ModerationResultDto;
import com.ravenpacktest.moderationms.dto.UserMessageDto;
import com.ravenpacktest.moderationms.dto.UserMessageScoreDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ModerationServiceImplTest {

    public static final String MESSAGE = "message";
    public static final String MESSAGE_1 = "message1";
    public static final String MESSAGE_2 = "message2";
    public static final String MESSAGE_3 = "message3";
    public static final String MESSAGE_4 = "message4";
    public static final String MESSAGE_5 = "message5";
    public static final String MESSAGE_6 = "message6";
    public static final String MESSAGE_7 = "message7";
    public static final String MESSAGE_8 = "message8";
    public static final String MESSAGE_9 = "message9";
    @Mock
    private AsyncMessageScoreHandler asyncMessageScoreHandler;

    @InjectMocks
    private ModerationServiceImpl moderationService;

    @Test
    void averageValueCalculatedCorrectlyForOneMessageFromOneUserTest() {
        UserMessageDto messageDto = new UserMessageDto("1", MESSAGE);
        when(asyncMessageScoreHandler.calculateMessageScore(messageDto)).thenReturn(CompletableFuture.completedFuture(new UserMessageScoreDto("1", MESSAGE, 0.5f)));
        List<ModerationResultDto> moderationResultDtos = moderationService.calculateModerationResults(List.of(messageDto));
        assertEquals(0.5f, moderationResultDtos.get(0).averageModerationScore());
    }

    @Test
    void amountOfMessagesCalculatedCorrectlyForOneMessageFromOneUserTest() {
        UserMessageDto messageDto = new UserMessageDto("1", MESSAGE);
        when(asyncMessageScoreHandler.calculateMessageScore(messageDto)).thenReturn(CompletableFuture.completedFuture(new UserMessageScoreDto("1", MESSAGE, 0.5f)));
        List<ModerationResultDto> moderationResultDtos = moderationService.calculateModerationResults(List.of(messageDto));
        assertEquals(1, moderationResultDtos.get(0).messagesAmount());
    }

    @Test
    void averageValueCalculatedCorrectlyForThreeMessageFromOneUserTest() {
        UserMessageDto messageDto01 = new UserMessageDto("1", MESSAGE_1);
        UserMessageDto messageDto02 = new UserMessageDto("1", MESSAGE_2);
        UserMessageDto messageDto03 = new UserMessageDto("1", MESSAGE_3);
        when(asyncMessageScoreHandler.calculateMessageScore(messageDto01)).thenReturn(CompletableFuture.completedFuture(new UserMessageScoreDto("1", MESSAGE_1, 0.5f)));
        when(asyncMessageScoreHandler.calculateMessageScore(messageDto02)).thenReturn(CompletableFuture.completedFuture(new UserMessageScoreDto("1", MESSAGE_2, 0.75f)));
        when(asyncMessageScoreHandler.calculateMessageScore(messageDto03)).thenReturn(CompletableFuture.completedFuture(new UserMessageScoreDto("1", MESSAGE_3, 1f)));
        List<ModerationResultDto> moderationResultDtos = moderationService.calculateModerationResults(List.of(messageDto01, messageDto02, messageDto03));
        assertEquals(0.75f, moderationResultDtos.get(0).averageModerationScore());
    }

    @Test
    void amountOfMessagesCalculatedCorrectlyForThreeMessageFromOneUserTest() {
        UserMessageDto messageDto01 = new UserMessageDto("1", MESSAGE_1);
        UserMessageDto messageDto02 = new UserMessageDto("1", MESSAGE_2);
        UserMessageDto messageDto03 = new UserMessageDto("1", MESSAGE_3);
        when(asyncMessageScoreHandler.calculateMessageScore(messageDto01)).thenReturn(CompletableFuture.completedFuture(new UserMessageScoreDto("1", MESSAGE_1, 0.5f)));
        when(asyncMessageScoreHandler.calculateMessageScore(messageDto02)).thenReturn(CompletableFuture.completedFuture(new UserMessageScoreDto("1", MESSAGE_2, 0.75f)));
        when(asyncMessageScoreHandler.calculateMessageScore(messageDto03)).thenReturn(CompletableFuture.completedFuture(new UserMessageScoreDto("1", MESSAGE_3, 1f)));
        List<ModerationResultDto> moderationResultDtos = moderationService.calculateModerationResults(List.of(messageDto01, messageDto02, messageDto03));
        assertEquals(3, moderationResultDtos.get(0).messagesAmount());
    }

    @Test
    void averageValueCalculatedCorrectlyForOneMessageFromThreeUsersTest() {
        UserMessageDto messageDto01 = new UserMessageDto("1", MESSAGE_1);
        UserMessageDto messageDto02 = new UserMessageDto("2", MESSAGE_2);
        UserMessageDto messageDto03 = new UserMessageDto("3", MESSAGE_3);
        when(asyncMessageScoreHandler.calculateMessageScore(messageDto01)).thenReturn(CompletableFuture.completedFuture(new UserMessageScoreDto("1", MESSAGE_1, 0.5f)));
        when(asyncMessageScoreHandler.calculateMessageScore(messageDto02)).thenReturn(CompletableFuture.completedFuture(new UserMessageScoreDto("2", MESSAGE_2, 0.75f)));
        when(asyncMessageScoreHandler.calculateMessageScore(messageDto03)).thenReturn(CompletableFuture.completedFuture(new UserMessageScoreDto("3", MESSAGE_3, 1f)));
        List<ModerationResultDto> moderationResultDtos = moderationService.calculateModerationResults(List.of(messageDto01, messageDto02, messageDto03));
        assertEquals(0.5f, moderationResultDtos.get(0).averageModerationScore());
        assertEquals(0.75f, moderationResultDtos.get(1).averageModerationScore());
        assertEquals(1f, moderationResultDtos.get(2).averageModerationScore());
    }

    @Test
    void amountOfMessagesCalculatedCorrectlyForOneMessageFromThreeUsersTest() {
        UserMessageDto messageDto01 = new UserMessageDto("1", MESSAGE_1);
        UserMessageDto messageDto02 = new UserMessageDto("2", MESSAGE_2);
        UserMessageDto messageDto03 = new UserMessageDto("3", MESSAGE_3);
        when(asyncMessageScoreHandler.calculateMessageScore(messageDto01)).thenReturn(CompletableFuture.completedFuture(new UserMessageScoreDto("1", MESSAGE_1, 0.5f)));
        when(asyncMessageScoreHandler.calculateMessageScore(messageDto02)).thenReturn(CompletableFuture.completedFuture(new UserMessageScoreDto("2", MESSAGE_2, 0.75f)));
        when(asyncMessageScoreHandler.calculateMessageScore(messageDto03)).thenReturn(CompletableFuture.completedFuture(new UserMessageScoreDto("3", MESSAGE_3, 1f)));
        List<ModerationResultDto> moderationResultDtos = moderationService.calculateModerationResults(List.of(messageDto01, messageDto02, messageDto03));
        assertEquals(1, moderationResultDtos.get(0).messagesAmount());
        assertEquals(1, moderationResultDtos.get(1).messagesAmount());
        assertEquals(1, moderationResultDtos.get(2).messagesAmount());
    }

    @Test
    void averageValueCalculatedCorrectlyForThreeMessagesFromThreeUsersTest() {
        UserMessageDto messageDto01 = new UserMessageDto("1", MESSAGE_1);
        UserMessageDto messageDto02 = new UserMessageDto("1", MESSAGE_2);
        UserMessageDto messageDto03 = new UserMessageDto("1", MESSAGE_3);
        UserMessageDto messageDto04 = new UserMessageDto("2", MESSAGE_4);
        UserMessageDto messageDto05 = new UserMessageDto("2", MESSAGE_5);
        UserMessageDto messageDto06 = new UserMessageDto("2", MESSAGE_6);
        UserMessageDto messageDto07 = new UserMessageDto("3", MESSAGE_7);
        UserMessageDto messageDto08 = new UserMessageDto("3", MESSAGE_8);
        UserMessageDto messageDto09 = new UserMessageDto("3", MESSAGE_9);

        when(asyncMessageScoreHandler.calculateMessageScore(messageDto01)).thenReturn(CompletableFuture.completedFuture(new UserMessageScoreDto("1", MESSAGE_1, 0.1f)));
        when(asyncMessageScoreHandler.calculateMessageScore(messageDto02)).thenReturn(CompletableFuture.completedFuture(new UserMessageScoreDto("1", MESSAGE_2, 0.2f)));
        when(asyncMessageScoreHandler.calculateMessageScore(messageDto03)).thenReturn(CompletableFuture.completedFuture(new UserMessageScoreDto("1", MESSAGE_3, 0.3f)));
        when(asyncMessageScoreHandler.calculateMessageScore(messageDto04)).thenReturn(CompletableFuture.completedFuture(new UserMessageScoreDto("2", MESSAGE_4, 0.4f)));
        when(asyncMessageScoreHandler.calculateMessageScore(messageDto05)).thenReturn(CompletableFuture.completedFuture(new UserMessageScoreDto("2", MESSAGE_5, 0.5f)));
        when(asyncMessageScoreHandler.calculateMessageScore(messageDto06)).thenReturn(CompletableFuture.completedFuture(new UserMessageScoreDto("2", MESSAGE_6, 0.6f)));
        when(asyncMessageScoreHandler.calculateMessageScore(messageDto07)).thenReturn(CompletableFuture.completedFuture(new UserMessageScoreDto("3", MESSAGE_7, 0.7f)));
        when(asyncMessageScoreHandler.calculateMessageScore(messageDto08)).thenReturn(CompletableFuture.completedFuture(new UserMessageScoreDto("3", MESSAGE_8, 0.8f)));
        when(asyncMessageScoreHandler.calculateMessageScore(messageDto09)).thenReturn(CompletableFuture.completedFuture(new UserMessageScoreDto("3", MESSAGE_9, 0.9f)));
        List<ModerationResultDto> moderationResultDtos = moderationService.calculateModerationResults(
                List.of(
                        messageDto01,
                        messageDto02,
                        messageDto03,
                        messageDto04,
                        messageDto05,
                        messageDto06,
                        messageDto07,
                        messageDto08,
                        messageDto09));
        assertEquals(0.2f, moderationResultDtos.get(0).averageModerationScore());
        assertEquals(0.5f, moderationResultDtos.get(1).averageModerationScore());
        assertEquals(0.8f, moderationResultDtos.get(2).averageModerationScore());
    }

    @Test
    void amountOfMessagesCalculatedCorrectlyForThreeMessagesFromThreeUsersTest() {
        UserMessageDto messageDto01 = new UserMessageDto("1", MESSAGE_1);
        UserMessageDto messageDto02 = new UserMessageDto("1", MESSAGE_2);
        UserMessageDto messageDto03 = new UserMessageDto("1", MESSAGE_3);
        UserMessageDto messageDto04 = new UserMessageDto("2", MESSAGE_4);
        UserMessageDto messageDto05 = new UserMessageDto("2", MESSAGE_5);
        UserMessageDto messageDto06 = new UserMessageDto("2", MESSAGE_6);
        UserMessageDto messageDto07 = new UserMessageDto("3", MESSAGE_7);
        UserMessageDto messageDto08 = new UserMessageDto("3", MESSAGE_8);
        UserMessageDto messageDto09 = new UserMessageDto("3", MESSAGE_9);

        when(asyncMessageScoreHandler.calculateMessageScore(messageDto01)).thenReturn(CompletableFuture.completedFuture(new UserMessageScoreDto("1", MESSAGE_1, 0.1f)));
        when(asyncMessageScoreHandler.calculateMessageScore(messageDto02)).thenReturn(CompletableFuture.completedFuture(new UserMessageScoreDto("1", MESSAGE_2, 0.2f)));
        when(asyncMessageScoreHandler.calculateMessageScore(messageDto03)).thenReturn(CompletableFuture.completedFuture(new UserMessageScoreDto("1", MESSAGE_3, 0.3f)));
        when(asyncMessageScoreHandler.calculateMessageScore(messageDto04)).thenReturn(CompletableFuture.completedFuture(new UserMessageScoreDto("2", MESSAGE_4, 0.4f)));
        when(asyncMessageScoreHandler.calculateMessageScore(messageDto05)).thenReturn(CompletableFuture.completedFuture(new UserMessageScoreDto("2", MESSAGE_5, 0.5f)));
        when(asyncMessageScoreHandler.calculateMessageScore(messageDto06)).thenReturn(CompletableFuture.completedFuture(new UserMessageScoreDto("2", MESSAGE_6, 0.6f)));
        when(asyncMessageScoreHandler.calculateMessageScore(messageDto07)).thenReturn(CompletableFuture.completedFuture(new UserMessageScoreDto("3", MESSAGE_7, 0.7f)));
        when(asyncMessageScoreHandler.calculateMessageScore(messageDto08)).thenReturn(CompletableFuture.completedFuture(new UserMessageScoreDto("3", MESSAGE_8, 0.8f)));
        when(asyncMessageScoreHandler.calculateMessageScore(messageDto09)).thenReturn(CompletableFuture.completedFuture(new UserMessageScoreDto("3", MESSAGE_9, 0.9f)));
        List<ModerationResultDto> moderationResultDtos = moderationService.calculateModerationResults(
                List.of(messageDto01,
                        messageDto02,
                        messageDto03,
                        messageDto04,
                        messageDto05,
                        messageDto06,
                        messageDto07,
                        messageDto08,
                        messageDto09));
        assertEquals(3, moderationResultDtos.get(0).messagesAmount());
        assertEquals(3, moderationResultDtos.get(1).messagesAmount());
        assertEquals(3, moderationResultDtos.get(2).messagesAmount());
    }

}