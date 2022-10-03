package ru.neoflex.trainingcenter.msdeal.controller;

import feign.FeignException;
import liquibase.repackaged.org.apache.commons.lang3.exception.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ru.neoflex.trainingcenter.msdeal.exception.EntityNotFoundException;
import ru.neoflex.trainingcenter.msdeal.exception.ExceptionMessage;
import ru.neoflex.trainingcenter.msdeal.exception.ScoringException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class DealExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionMessage> handleEntityNotFoundException(EntityNotFoundException e,
                                                                          HttpServletRequest request) {
        log.error("{} = {}", e.getClass(), ExceptionUtils.getStackTrace(e));

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ExceptionMessage.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.NOT_FOUND.value())
                        .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                        .message(e.getLocalizedMessage())
                        .path(request.getRequestURI())
                        .build());
    }

    @ExceptionHandler(ScoringException.class)
    public ResponseEntity<ExceptionMessage> handleScoringException(ScoringException e,
                                                                   HttpServletRequest request) {
        log.error("{} = {}", e.getClass(), ExceptionUtils.getStackTrace(e));

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ExceptionMessage.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                        .error(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase())
                        .message(e.getLocalizedMessage())
                        .path(request.getRequestURI())
                        .build());
    }

    @ExceptionHandler(FeignException.FeignClientException.class)
    public ResponseEntity<String> handleFeignClientException(FeignException.FeignClientException e) {
        log.error("{} = {}", e.getClass(), ExceptionUtils.getStackTrace(e));

        return ResponseEntity.status(e.status())
                .body(e.responseBody()
                        .map(byteBuffer -> new String(byteBuffer.array()))
                        .orElseThrow(() -> new RuntimeException(e)));
    }
}
