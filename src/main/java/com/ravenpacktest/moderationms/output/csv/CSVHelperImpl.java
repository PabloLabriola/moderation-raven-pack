package com.ravenpacktest.moderationms.output.csv;


import com.ravenpacktest.moderationms.dto.ModerationResultDto;
import com.ravenpacktest.moderationms.dto.UserMessageDto;
import com.ravenpacktest.moderationms.exception.FileProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class CSVHelperImpl implements CSVHelper {

    private static final String INVALID_FILE_MESSAGE = "File is invalid";
    private static final String FAIL_TO_IMPORT_DATA_MESSAGE = "Fail to import data to CSV file: %s";
    private static final String USER_ID_HEADER = "userId";
    private static final String MESSAGE_HEADER = "message";
    private static final String MESSAGES_AMOUNT_HEADER = "messagesAmount";
    private static final String AVERAGE_MODERATION_SCORE_HEADER = "avgModerationScore";
    private static final String[] USER_MESSAGES_HEADERS = {
            USER_ID_HEADER,
            MESSAGE_HEADER
    };
    private static final String[] MODERATION_RESULTS_HEADERS = {
            USER_ID_HEADER,
            MESSAGES_AMOUNT_HEADER,
            AVERAGE_MODERATION_SCORE_HEADER
    };

    @Override
    public List<UserMessageDto> getUsersMessagesFromFile(final InputStream csvFileStream) {
        final List<UserMessageDto> userMessagesFromCsv = new ArrayList<>();
        try {
            final Reader reader = new InputStreamReader(csvFileStream);
            final Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder()
                                                                 .setHeader(USER_MESSAGES_HEADERS)
                                                                 .setSkipHeaderRecord(true)
                                                                 .build()
                                                                 .parse(reader);
            for (final CSVRecord csvRecord : records) {
                final UserMessageDto userMessage = new UserMessageDto(csvRecord.get(USER_ID_HEADER), csvRecord.get(MESSAGE_HEADER));
                userMessagesFromCsv.add(userMessage);
            }
        } catch (IOException | IllegalArgumentException e) {
            log.warn(e.getMessage(), e);
            throw new FileProcessingException(INVALID_FILE_MESSAGE);
        }
        return userMessagesFromCsv;
    }

    @Override
    public InputStream getFileFromModerationResults(final List<ModerationResultDto> moderationResults) {
        final CSVFormat format = CSVFormat.DEFAULT.builder()
                                                  .setHeader(MODERATION_RESULTS_HEADERS)
                                                  .build();

        try (final ByteArrayOutputStream out = new ByteArrayOutputStream();
             final CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {

            for (final ModerationResultDto moderationResult : moderationResults) {
                final List<String> data = Arrays.asList(
                        moderationResult.userId(),
                        String.valueOf(moderationResult.messagesAmount()),
                        String.valueOf(moderationResult.averageModerationScore())
                );

                csvPrinter.printRecord(data);
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new FileProcessingException(FAIL_TO_IMPORT_DATA_MESSAGE.formatted(e.getMessage()));
        }
    }
}
