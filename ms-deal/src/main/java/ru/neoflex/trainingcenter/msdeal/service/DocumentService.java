package ru.neoflex.trainingcenter.msdeal.service;

import java.util.UUID;

public interface DocumentService {

    void send(UUID applicationId);

    void sign(UUID applicationId);

    void code(UUID applicationId);
}
