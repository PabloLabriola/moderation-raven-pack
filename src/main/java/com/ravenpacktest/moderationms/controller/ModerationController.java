package com.ravenpacktest.moderationms.controller;

import com.ravenpacktest.moderationms.output.ModerationResultOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/message-moderation")
public class ModerationController {

    private static final String FILE_NAME = "moderation-results.csv";

    private final ModerationResultOutput moderationResultOutput;

    @PostMapping
    public ResponseEntity<Resource> calculateModerationResults(@RequestParam("file-to-moderate") MultipartFile fileToModerate) throws IOException {
        final InputStream moderationResultsFromCsvFile = moderationResultOutput.getModerationResultFromCsvFile(fileToModerate.getInputStream());
        final InputStreamResource responseFile = new InputStreamResource(moderationResultsFromCsvFile);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + FILE_NAME)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(responseFile);
    }
}

