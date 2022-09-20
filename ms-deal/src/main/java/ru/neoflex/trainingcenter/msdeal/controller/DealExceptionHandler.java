package ru.neoflex.trainingcenter.msdeal.controller;

import feign.FeignException;
import liquibase.repackaged.org.apache.commons.lang3.exception.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.neoflex.trainingcenter.msdeal.exception.EntityNotFoundException;

@Slf4j
@RestControllerAdvice
public class DealExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> handleEntityNotFoundException(EntityNotFoundException e) {
        log.error("{} = {}", e.getClass(), ExceptionUtils.getStackTrace(e));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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
