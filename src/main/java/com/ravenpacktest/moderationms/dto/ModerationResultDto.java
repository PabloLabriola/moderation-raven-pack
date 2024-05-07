package com.ravenpacktest.moderationms.dto;

public record ModerationResultDto(String userId,
                                  Long messagesAmount,
                                  float averageModerationScore) {
}
