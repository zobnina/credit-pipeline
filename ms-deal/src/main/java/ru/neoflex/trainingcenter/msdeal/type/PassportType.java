package ru.neoflex.trainingcenter.msdeal.type;

import ru.neoflex.trainingcenter.msdeal.model.entity.Passport;

public class PassportType extends JsonBType {

    @Override
    public Class<?> returnedClass() {

        return Passport.class;
    }
}
