package ru.neoflex.trainingcenter.msconveyor.config;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class Constant {

    public static final BigDecimal HUNDRED = BigDecimal.valueOf(100);
    public static final int SCALE = 5;
    public static final int RATE_SCALE = 2;
    public static final int MONTHS_PER_YEAR = 12;
    public static final int MIN_SALARIES_COUNT = 20;
    public static final int MIN_AGE = 20;
    public static final int MAX_AGE = 60;
    public static final int MIN_FEMALE_AGE = 35;
    public static final int MIN_MALE_AGE = 30;
    public static final int MAX_MALE_AGE = 55;
    public static final int BUSINESS_OWNER_RATE_INCREASE = 3;
    public static final int MID_MANAGER_RATE_DECREASE = 2;
    public static final int TOP_MANAGER_RATE_DECREASE = 4;
    public static final int MARRIED_RATE_DECREASE = 3;
    public static final int MIN_DEPENDENT_AMOUNT = 1;
    public static final int GENDER_RATE = 3;
}
