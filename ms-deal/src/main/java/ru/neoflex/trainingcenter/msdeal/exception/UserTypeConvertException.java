package ru.neoflex.trainingcenter.msdeal.exception;

public class UserTypeConvertException extends RuntimeException {

    private static final String MESSAGE = "Failed to convert: ";

    public UserTypeConvertException(Throwable e) {
        super(MESSAGE + e.getMessage(), e);
    }
}
