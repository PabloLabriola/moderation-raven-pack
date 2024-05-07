package com.ravenpacktest.moderationms.output.csv;

import com.ravenpacktest.moderationms.dto.ModerationResultDto;
import com.ravenpacktest.moderationms.dto.UserMessageDto;

import java.io.InputStream;
import java.util.List;

public interface CSVHelper {

    List<UserMessageDto> getUsersMessagesFromFile(InputStream csvFileStream);

    InputStream getFileFromModerationResults(List<ModerationResultDto> moderationResults);
}
