package ru.neoflex.trainingcenter.msdeal.mapper;

import org.mapstruct.Mapper;
import ru.neoflex.trainingcenter.msdeal.model.ApplicationStatus;
import ru.neoflex.trainingcenter.msdeal.model.entity.Application;
import ru.neoflex.trainingcenter.msdeal.model.entity.Client;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    default Application createApplication(Client client) {

        return Application.builder()
                .client(client)
                .status(ApplicationStatus.PREAPPROVAL)
                .creationDate(LocalDate.now())
                .build();
    }
}
