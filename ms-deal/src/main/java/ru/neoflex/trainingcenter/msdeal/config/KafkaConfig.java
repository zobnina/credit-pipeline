package ru.neoflex.trainingcenter.msdeal.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import ru.azobnina.liblog.kafka.LogProducerListener;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    private final ProducerFactory<String, String> producerFactory;

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {

        KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(producerFactory);
        kafkaTemplate.setProducerListener(new LogProducerListener());

        return kafkaTemplate;
    }
}
