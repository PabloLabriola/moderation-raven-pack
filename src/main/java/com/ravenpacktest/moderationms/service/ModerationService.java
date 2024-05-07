package com.ravenpacktest.moderationms.service;

import com.ravenpacktest.moderationms.dto.ModerationResultDto;
import com.ravenpacktest.moderationms.dto.UserMessageDto;

import java.util.List;

public interface ModerationService {

    List<ModerationResultDto> calculateModerationResults(List<UserMessageDto> usersMessages);
}
