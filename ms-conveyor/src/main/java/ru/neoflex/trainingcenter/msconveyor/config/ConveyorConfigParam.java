package ru.neoflex.trainingcenter.msconveyor.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.neoflex.trainingcenter.msconveyor.config.prop.Amount;
import ru.neoflex.trainingcenter.msconveyor.config.prop.Insurance;
import ru.neoflex.trainingcenter.msconveyor.config.prop.Rate;
import ru.neoflex.trainingcenter.msconveyor.config.prop.Term;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "conveyor")
public class ConveyorConfigParam {

    private Term term;

    private Amount amount;

    private Rate rate;

    private Insurance insurance;
}
