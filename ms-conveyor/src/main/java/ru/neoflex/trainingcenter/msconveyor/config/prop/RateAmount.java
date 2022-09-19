package ru.neoflex.trainingcenter.msconveyor.config.prop;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RateAmount {

    private BigDecimal small;

    private BigDecimal middle;

    private BigDecimal big;
}
