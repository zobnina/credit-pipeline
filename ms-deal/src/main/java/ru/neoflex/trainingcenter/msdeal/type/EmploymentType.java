package ru.neoflex.trainingcenter.msdeal.type;

import ru.neoflex.trainingcenter.msdeal.model.Employment;

public class EmploymentType extends JsonBType {

    @Override
    public Class<?> returnedClass() {

        return Employment.class;
    }
}
