package com.ravenpacktest.moderationms.exception;

import com.holovenko.moderation.http.exception.ApiResourceNotFoundException;
import com.holovenko.moderation.http.exception.InvalidApiResponseException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionMapper {

    @ExceptionHandler(FileProcessingException.class)
    public ResponseEntity<ApiException> handleFileProcessingException(final FileProcessingException ex,
                                                                      final HttpServletRequest request) {
        return exceptionResponse(ex.getMessage(), ex, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ModerationServerException.class)
    public ResponseEntity<ApiException> handleServerException(final ModerationServerException ex,
                                                              final HttpServletRequest request) {
        return exceptionResponse(ex.getMessage(), ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ApiResourceNotFoundException.class)
    public ResponseEntity<ApiException> handleApiResourceNotFoundException(final ApiResourceNotFoundException ex,
                                                                           final HttpServletRequest request) {
        return exceptionResponse(ex.getMessage(), ex, request, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(InvalidApiResponseException.class)
    public ResponseEntity<ApiException> handleInvalidApiResponseException(final ApiResourceNotFoundException ex,
                                                                          final HttpServletRequest request) {
        return exceptionResponse(ex.getMessage(), ex, request, HttpStatus.BAD_GATEWAY);
    }

    private ResponseEntity<ApiException> exceptionResponse(final String errorMessage,
                                                           final Throwable exception,
                                                           final HttpServletRequest request,
                                                           final HttpStatus status) {
        log.error(exception.getMessage(), exception);

        final ApiException apiException = new ApiException(status, request.getRequestURI(), errorMessage);
        return new ResponseEntity<>(apiException, status);
    }
}
