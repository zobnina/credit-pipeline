package ru.neoflex.trainingcenter.msdeal.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import ru.neoflex.trainingcenter.msdeal.model.dto.FinishRegistrationRequestDto;
import ru.neoflex.trainingcenter.msdeal.model.dto.LoanOfferDto;
import ru.neoflex.trainingcenter.msdeal.model.dto.ScoringDataDto;
import ru.neoflex.trainingcenter.msdeal.model.entity.Application;
import ru.neoflex.trainingcenter.msdeal.model.entity.Client;
import ru.neoflex.trainingcenter.msdeal.model.entity.Passport;

@Mapper(componentModel = "spring")
public interface ScoringDataMapper {

    ScoringDataMapper MAPPER = Mappers.getMapper(ScoringDataMapper.class);

    @Mapping(target = "amount", ignore = true)
    @Mapping(target = "term", ignore = true)
    @Mapping(target = "passportSeries", ignore = true)
    @Mapping(target = "passportNumber", ignore = true)
    @Mapping(target = "passportIssueDate", ignore = true)
    @Mapping(target = "passportIssueBranch", ignore = true)
    @Mapping(target = "isInsuranceEnabled", ignore = true)
    @Mapping(target = "isSalaryClient", ignore = true)
    @Mapping(target = "birthdate", source = "birthDate")
    ScoringDataDto clientToScoringDataDto(Client client);

    @Mapping(target = "amount", ignore = true)
    @Mapping(target = "term", ignore = true)
    @Mapping(target = "firstName", ignore = true)
    @Mapping(target = "lastName", ignore = true)
    @Mapping(target = "middleName", ignore = true)
    @Mapping(target = "gender", ignore = true)
    @Mapping(target = "birthdate", ignore = true)
    @Mapping(target = "passportIssueDate", ignore = true)
    @Mapping(target = "passportIssueBranch", ignore = true)
    @Mapping(target = "maritalStatus", ignore = true)
    @Mapping(target = "dependentAmount", ignore = true)
    @Mapping(target = "employment", ignore = true)
    @Mapping(target = "account", ignore = true)
    @Mapping(target = "isInsuranceEnabled", ignore = true)
    @Mapping(target = "isSalaryClient", ignore = true)
    @Mapping(target = "passportSeries", source = "series")
    @Mapping(target = "passportNumber", source = "number")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void update(@MappingTarget ScoringDataDto scoringDataDto, Passport passport);

    @Mapping(target = "amount", ignore = true)
    @Mapping(target = "term", ignore = true)
    @Mapping(target = "firstName", ignore = true)
    @Mapping(target = "lastName", ignore = true)
    @Mapping(target = "middleName", ignore = true)
    @Mapping(target = "birthdate", ignore = true)
    @Mapping(target = "passportSeries", ignore = true)
    @Mapping(target = "passportNumber", ignore = true)
    @Mapping(target = "isInsuranceEnabled", ignore = true)
    @Mapping(target = "isSalaryClient", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void update(@MappingTarget ScoringDataDto scoringDataDto, FinishRegistrationRequestDto finishRegistrationRequestDto);

    @Mapping(target = "firstName", ignore = true)
    @Mapping(target = "lastName", ignore = true)
    @Mapping(target = "middleName", ignore = true)
    @Mapping(target = "gender", ignore = true)
    @Mapping(target = "birthdate", ignore = true)
    @Mapping(target = "passportSeries", ignore = true)
    @Mapping(target = "passportNumber", ignore = true)
    @Mapping(target = "passportIssueDate", ignore = true)
    @Mapping(target = "passportIssueBranch", ignore = true)
    @Mapping(target = "maritalStatus", ignore = true)
    @Mapping(target = "dependentAmount", ignore = true)
    @Mapping(target = "employment", ignore = true)
    @Mapping(target = "account", ignore = true)
    @Mapping(target = "amount", source = "requestAmount")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void update(@MappingTarget ScoringDataDto scoringDataDto, LoanOfferDto loanOfferDto);

    default ScoringDataDto createScoringDataDto(Application application,
                                                FinishRegistrationRequestDto finishRegistrationRequestDto) {

        ScoringDataDto scoringDataDto = clientToScoringDataDto(application.getClient());
        update(scoringDataDto, application.getClient().getPassport());
        update(scoringDataDto, finishRegistrationRequestDto);
        update(scoringDataDto, application.getAppliedOffer());

        return scoringDataDto;
    }
}
