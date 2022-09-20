package ru.neoflex.trainingcenter.msdeal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.neoflex.trainingcenter.msdeal.model.dto.LoanApplicationRequestDto;
import ru.neoflex.trainingcenter.msdeal.model.entity.Client;
import ru.neoflex.trainingcenter.msdeal.model.entity.Passport;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "birthDate", source = "birthdate")
    Client loanApplicationRequestDtoToClient(LoanApplicationRequestDto loanApplicationRequestDto);

    default Client createClient(LoanApplicationRequestDto loanApplicationRequestDto,
                                Passport passport) {

        Client client = loanApplicationRequestDtoToClient(loanApplicationRequestDto);
        client.setPassport(passport);

        return client;
    }
}
