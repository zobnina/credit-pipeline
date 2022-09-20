package ru.neoflex.trainingcenter.msdeal.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.neoflex.trainingcenter.msdeal.exception.EntityNotFoundException;
import ru.neoflex.trainingcenter.msdeal.model.ApplicationStatus;
import ru.neoflex.trainingcenter.msdeal.model.entity.Application;
import ru.neoflex.trainingcenter.msdeal.repo.ApplicationRepository;
import ru.neoflex.trainingcenter.msdeal.config.Constant;
import ru.neoflex.trainingcenter.msdeal.service.DocumentService;
import ru.neoflex.trainingcenter.msdeal.service.EmailMessageProducer;
import ru.neoflex.trainingcenter.liblog.DebugLog;

import java.time.LocalDate;
import java.util.UUID;

@DebugLog
@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final EmailMessageProducer emailMessageProducer;
    private final ApplicationRepository applicationRepository;

    @Override
    public void send(UUID applicationId) {

        final Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new EntityNotFoundException(Constant.APPLICATION));
        application.setStatus(ApplicationStatus.PREPARE_DOCUMENTS);
        emailMessageProducer.sendDocuments(application.getId(), application.getClient().getEmail());
    }

    @Override
    public void sign(UUID applicationId) {

        final Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new EntityNotFoundException(Constant.APPLICATION));
        application.setSesCode(UUID.randomUUID().toString());
        emailMessageProducer.signDocuments(application.getId(), application.getClient().getEmail());
    }

    @Override
    public void code(UUID applicationId) {

        final Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new EntityNotFoundException(Constant.APPLICATION));
        application.setStatus(ApplicationStatus.DOCUMENT_SIGNED);
        application.setSignDate(LocalDate.now());
        emailMessageProducer.codeDocuments(application.getId(), application.getClient().getEmail());
    }
}
