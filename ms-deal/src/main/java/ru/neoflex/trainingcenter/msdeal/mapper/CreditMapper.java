package ru.neoflex.trainingcenter.msdeal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.neoflex.trainingcenter.msdeal.model.dto.CreditDto;
import ru.neoflex.trainingcenter.msdeal.model.entity.Credit;

@Mapper(componentModel = "spring")
public interface CreditMapper {

    CreditMapper MAPPER = Mappers.getMapper(CreditMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creditStatus", constant = "CALCULATED")
    Credit creditDtoToCredit(CreditDto creditDto);
}
