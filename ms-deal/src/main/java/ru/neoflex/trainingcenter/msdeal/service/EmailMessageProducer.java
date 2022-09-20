package ru.neoflex.trainingcenter.msdeal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.neoflex.trainingcenter.msdeal.config.KafkaTopic;
import ru.neoflex.trainingcenter.msdeal.model.dossier.EmailMessage;
import ru.neoflex.trainingcenter.msdeal.model.dossier.Theme;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailMessageProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaTopic kafkaTopic;

    private final ObjectMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public void finishRegistration(UUID applicationId, String address) {

        EmailMessage emailMessage = new EmailMessage(applicationId, address, Theme.FINISH_REGISTRATION);
        sendMessage(kafkaTopic.getFinishRegistrationTopic(), emailMessage);
    }

    public void createDocuments(UUID applicationId, String address) {

        EmailMessage emailMessage = new EmailMessage(applicationId, address, Theme.CREATE_DOCUMENTS);
        sendMessage(kafkaTopic.getCreateDocuments(), emailMessage);
    }

    public void sendDocuments(UUID applicationId, String address) {

        EmailMessage emailMessage = new EmailMessage(applicationId, address, Theme.SEND_DOCUMENTS);
        sendMessage(kafkaTopic.getSendDocuments(), emailMessage);
    }

    public void signDocuments(UUID applicationId, String address) {

        EmailMessage emailMessage = new EmailMessage(applicationId, address, Theme.SEND_SES);
        sendMessage(kafkaTopic.getSendSes(), emailMessage);
    }


    public void codeDocuments(UUID applicationId, String address) {

        EmailMessage emailMessage = new EmailMessage(applicationId, address, Theme.CREDIT_ISSUED);
        sendMessage(kafkaTopic.getCreditIssued(), emailMessage);
    }

    private void sendMessage(String topic, EmailMessage data) {

        executorService.submit(() -> {
            try {
                kafkaTemplate.send(topic, objectMapper.writeValueAsString(data));
            } catch (JsonProcessingException e) {
                log.error("{} = {}", e.getClass().getName(), ExceptionUtils.getStackTrace(e));
            }
        });
    }
}
