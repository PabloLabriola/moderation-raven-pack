package com.ravenpacktest.moderationms.dto;

public record UserMessageScoreDto(String userId,
                                  String translatedMessage,
                                  float score) {
}
