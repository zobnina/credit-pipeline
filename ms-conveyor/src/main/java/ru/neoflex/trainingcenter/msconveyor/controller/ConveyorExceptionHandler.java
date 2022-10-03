package ru.neoflex.trainingcenter.msconveyor.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.neoflex.trainingcenter.msconveyor.exception.ExceptionMessage;
import ru.neoflex.trainingcenter.msconveyor.exception.ScoringException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class ConveyorExceptionHandler {

    @ExceptionHandler(ScoringException.class)
    public ResponseEntity<ExceptionMessage> scoringExceptionHandle(ScoringException e,
                                                                   HttpServletRequest request) {
        log.error("{} = {}", e.getClass(), ExceptionUtils.getStackTrace(e));

        return ResponseEntity.unprocessableEntity()
                .body(ExceptionMessage.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                        .error(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase())
                        .message(e.getLocalizedMessage())
                        .path(request.getRequestURI())
                        .build());
    }
}
