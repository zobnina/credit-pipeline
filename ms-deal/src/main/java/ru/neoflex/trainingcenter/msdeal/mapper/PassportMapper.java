package ru.neoflex.trainingcenter.msdeal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.neoflex.trainingcenter.msdeal.model.dto.LoanApplicationRequestDto;
import ru.neoflex.trainingcenter.msdeal.model.entity.Passport;

@Mapper(componentModel = "spring")
public interface PassportMapper {

    PassportMapper MAPPER = Mappers.getMapper(PassportMapper.class);

    @Mapping(target = "issueBranch", ignore = true)
    @Mapping(target = "issueDate", ignore = true)
    @Mapping(target = "series", source = "passportSeries")
    @Mapping(target = "number", source = "passportNumber")
    Passport loanApplicationRequestDtoToPassport(LoanApplicationRequestDto loanApplicationRequestDto);
}
