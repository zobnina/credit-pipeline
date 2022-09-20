package ru.neoflex.trainingcenter.msdeal.type;

import ru.neoflex.trainingcenter.msdeal.model.dto.LoanOfferDto;

public class LoanOfferType extends JsonBType {

    @Override
    public Class<?> returnedClass() {

        return LoanOfferDto.class;
    }
}
