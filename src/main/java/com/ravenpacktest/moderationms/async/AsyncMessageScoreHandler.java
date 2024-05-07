package com.ravenpacktest.moderationms.async;

import com.ravenpacktest.moderationms.dto.UserMessageDto;
import com.ravenpacktest.moderationms.dto.UserMessageScoreDto;

import java.util.concurrent.CompletableFuture;

public interface AsyncMessageScoreHandler {

    CompletableFuture<UserMessageScoreDto> calculateMessageScore(UserMessageDto userMessageDto);
}
