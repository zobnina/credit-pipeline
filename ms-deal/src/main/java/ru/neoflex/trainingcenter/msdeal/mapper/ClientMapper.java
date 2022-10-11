package ru.neoflex.trainingcenter.msdeal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.neoflex.trainingcenter.msdeal.model.dto.LoanApplicationRequestDto;
import ru.neoflex.trainingcenter.msdeal.model.entity.Client;
import ru.neoflex.trainingcenter.msdeal.model.entity.Passport;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientMapper MAPPER = Mappers.getMapper(ClientMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "gender", ignore = true)
    @Mapping(target = "maritalStatus", ignore = true)
    @Mapping(target = "dependentAmount", ignore = true)
    @Mapping(target = "employment", ignore = true)
    @Mapping(target = "account", ignore = true)
    @Mapping(target = "applications", ignore = true)
    @Mapping(target = "birthDate", source = "loanApplicationRequestDto.birthdate")
    @Mapping(target = "firstName", source = "loanApplicationRequestDto.firstName")
    @Mapping(target = "lastName", source = "loanApplicationRequestDto.lastName")
    @Mapping(target = "middleName", source = "loanApplicationRequestDto.middleName")
    @Mapping(target = "email", source = "loanApplicationRequestDto.email")
    @Mapping(target = "passport", source = "passport")
    Client createClient(LoanApplicationRequestDto loanApplicationRequestDto, Passport passport);
}
