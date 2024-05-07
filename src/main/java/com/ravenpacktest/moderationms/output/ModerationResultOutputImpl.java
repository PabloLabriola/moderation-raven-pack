package com.ravenpacktest.moderationms.output;

import com.ravenpacktest.moderationms.dto.ModerationResultDto;
import com.ravenpacktest.moderationms.dto.UserMessageDto;
import com.ravenpacktest.moderationms.output.csv.CSVHelper;
import com.ravenpacktest.moderationms.service.ModerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ModerationResultOutputImpl implements ModerationResultOutput {

    private final CSVHelper csvHelper;
    private final ModerationService moderationService;

    @Override
    public InputStream getModerationResultFromCsvFile(final InputStream csvFile) {
        final List<UserMessageDto> usersMessagesFromFile = csvHelper.getUsersMessagesFromFile(csvFile);
        final List<ModerationResultDto> moderationResults = moderationService.calculateModerationResults(usersMessagesFromFile);
        return csvHelper.getFileFromModerationResults(moderationResults);
    }
}
