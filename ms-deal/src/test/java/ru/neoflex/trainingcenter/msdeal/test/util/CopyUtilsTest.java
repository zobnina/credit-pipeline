package ru.neoflex.trainingcenter.msdeal.test.util;

import org.junit.jupiter.api.Test;
import ru.neoflex.trainingcenter.msdeal.model.Employment;
import ru.neoflex.trainingcenter.msdeal.model.EmploymentStatus;
import ru.neoflex.trainingcenter.msdeal.util.CopyUtils;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CopyUtilsTest {

    @Test
    void nullPropertyNamesTest() {

        Employment employment = new Employment();
        employment.setEmploymentStatus(EmploymentStatus.EMPLOYED);
        employment.setSalary(BigDecimal.ONE);
        String[] nullPropNames = CopyUtils.getNullPropertyNames(employment);

        assertEquals(4, nullPropNames.length);
        assertTrue(Arrays.stream(nullPropNames).noneMatch("employmentStatus"::equals));
        assertTrue(Arrays.asList(nullPropNames).contains("position"));
    }

    @Test
    void copyPropertiesTest() {

        Employment src = new Employment();
        src.setEmploymentStatus(EmploymentStatus.EMPLOYED);
        src.setSalary(BigDecimal.ONE);
        Employment target = new Employment();

        CopyUtils.copyProperties(src, target);
        assertEquals(src.getEmploymentStatus(), target.getEmploymentStatus());
        assertEquals(src.getSalary(), target.getSalary());
    }
}
