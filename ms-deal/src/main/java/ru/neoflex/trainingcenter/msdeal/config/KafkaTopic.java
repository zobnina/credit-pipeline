package ru.neoflex.trainingcenter.msdeal.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class KafkaTopic {

    @Value("${kafka.topic.finish-registration}")
    private String finishRegistrationTopic;

    @Value("${kafka.topic.create-documents}")
    private String createDocuments;

    @Value("${kafka.topic.send-documents}")
    private String sendDocuments;

    @Value("${kafka.topic.send-ses}")
    private String sendSes;

    @Value("${kafka.topic.credit-issued}")
    private String creditIssued;
}
