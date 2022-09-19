package ru.neoflex.trainingcenter.msconveyor.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.neoflex.trainingcenter.msconveyor.exception.ScoringException;

@Slf4j
@RestControllerAdvice
public class ConveyorExceptionHandler {

    @ExceptionHandler(ScoringException.class)
    public ResponseEntity<Void> scoringExceptionHandle(ScoringException e) {
        log.error("{} = {}", e.getClass(), ExceptionUtils.getStackTrace(e));

        return ResponseEntity.unprocessableEntity().build();
    }
}
