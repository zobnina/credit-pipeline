package ru.neoflex.trainingcenter.msconveyor.exception;

public class ScoringException extends RuntimeException {

    public ScoringException(ScoringExceptionReason scoringExceptionReason) {
        super(scoringExceptionReason.getMessage());
    }
}
