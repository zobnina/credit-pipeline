package ru.neoflex.trainingcenter.msdeal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.neoflex.trainingcenter.msdeal.model.CreditStatus;
import ru.neoflex.trainingcenter.msdeal.model.dto.CreditDto;
import ru.neoflex.trainingcenter.msdeal.model.entity.Credit;

@Mapper(componentModel = "spring")
public interface CreditMapper {

    @Mapping(target = "creditStatus", expression = "java(calculatedStatus())")
    Credit creditDtoToCredit(CreditDto creditDto);

    default CreditStatus calculatedStatus() {

        return CreditStatus.CALCULATED;
    }
}
