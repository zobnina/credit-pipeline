package ru.neoflex.trainingcenter.msdeal.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.neoflex.trainingcenter.msdeal.model.dto.FinishRegistrationRequestDto;
import ru.neoflex.trainingcenter.msdeal.model.dto.LoanOfferDto;
import ru.neoflex.trainingcenter.msdeal.model.dto.ScoringDataDto;
import ru.neoflex.trainingcenter.msdeal.model.entity.Application;
import ru.neoflex.trainingcenter.msdeal.model.entity.Client;
import ru.neoflex.trainingcenter.msdeal.model.entity.Passport;

@Mapper(componentModel = "spring")
public interface ScoringDataMapper {

    @Mapping(target = "birthdate", source = "birthDate")
    ScoringDataDto clientToScoringDataDto(Client client);

    @Mapping(target = "passportSeries", source = "series")
    @Mapping(target = "passportNumber", source = "number")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void update(@MappingTarget ScoringDataDto scoringDataDto, Passport passport);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void update(@MappingTarget ScoringDataDto scoringDataDto, FinishRegistrationRequestDto finishRegistrationRequestDto);

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
