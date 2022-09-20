package ru.neoflex.trainingcenter.msdeal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employment implements Serializable {

    @JsonProperty("employer_status")
    private EmploymentStatus employmentStatus;

    @JsonProperty("employer_inn")
    private String employerInn;

    private BigDecimal salary;

    private Position position;

    @JsonProperty("work_experience_total")
    private Integer workExperienceTotal;

    @JsonProperty("work_experience_current")
    private Integer workExperienceCurrent;
}
