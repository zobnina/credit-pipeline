package ru.neoflex.trainingcenter.msdeal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.neoflex.trainingcenter.msdeal.model.entity.Application;
import ru.neoflex.trainingcenter.msdeal.model.entity.Client;

import java.time.LocalDate;

@Mapper(componentModel = "spring", imports = LocalDate.class)
public interface ApplicationMapper {

    ApplicationMapper MAPPER = Mappers.getMapper(ApplicationMapper.class);

    @Mapping(target = "credit", ignore = true)
    @Mapping(target = "appliedOffer", ignore = true)
    @Mapping(target = "signDate", ignore = true)
    @Mapping(target = "sesCode", ignore = true)
    @Mapping(target = "statusHistory", ignore = true)
    @Mapping(target = "client", source = "client")
    @Mapping(target = "status", constant = "PREAPPROVAL")
    @Mapping(target = "creationDate", expression = "java(LocalDate.now())")
    Application createApplication(Client client);
}
