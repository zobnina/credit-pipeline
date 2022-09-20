package ru.neoflex.trainingcenter.msdeal.type;

import java.util.List;

public class ListType extends JsonBType {

    @Override
    public Class<?> returnedClass() {

        return List.class;
    }
}
