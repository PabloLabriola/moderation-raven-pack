package com.ravenpacktest.moderationms.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class ApiException {

    private final HttpStatus status;
    private final String path;
    private final String error;
}
