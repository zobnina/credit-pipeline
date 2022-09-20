package ru.neoflex.trainingcenter.msdeal.exception;

public class EntityNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Entity Not Found: ";

    public EntityNotFoundException(String entity) {
        super(MESSAGE + entity);
    }
}
