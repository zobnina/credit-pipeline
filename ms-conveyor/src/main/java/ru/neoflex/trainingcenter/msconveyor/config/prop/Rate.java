package ru.neoflex.trainingcenter.msconveyor.config.prop;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Rate {

    private BigDecimal base;

    private RateTerm term;

    private RateAmount amount;

    private BigDecimal insurance;

    private BigDecimal salaryClient;
}
