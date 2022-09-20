package ru.neoflex.trainingcenter.msdeal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.neoflex.trainingcenter.msdeal.model.dto.LoanApplicationRequestDto;
import ru.neoflex.trainingcenter.msdeal.model.entity.Passport;

@Mapper(componentModel = "spring")
public interface PassportMapper {

    @Mapping(target = "series", source = "passportSeries")
    @Mapping(target = "number", source = "passportNumber")
    Passport loanApplicationRequestDtoToPassport(LoanApplicationRequestDto loanApplicationRequestDto);
}
