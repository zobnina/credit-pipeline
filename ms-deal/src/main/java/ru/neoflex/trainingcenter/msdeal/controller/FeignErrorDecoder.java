package ru.neoflex.trainingcenter.msdeal.controller;

import com.fasterxml.jackson.databind.json.JsonMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;
import ru.neoflex.trainingcenter.msdeal.exception.ScoringException;
import ru.neoflex.trainingcenter.msdeal.exception.ExceptionMessage;

import java.io.IOException;
import java.io.InputStream;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder errorDecoder = new Default();
    private final JsonMapper mapper = JsonMapper.builder().findAndAddModules().build();

    @Override
    public Exception decode(String methodKey, Response response) {

        ExceptionMessage message;
        if (response.status() == 422) {
            try (InputStream bodyIs = response.body().asInputStream()) {
                message = mapper.readValue(bodyIs, ExceptionMessage.class);

                throw new ScoringException(message.getMessage());
            } catch (IOException e) {

                return new Exception(e.getMessage());
            }
        }

        return errorDecoder.decode(methodKey, response);
    }
}