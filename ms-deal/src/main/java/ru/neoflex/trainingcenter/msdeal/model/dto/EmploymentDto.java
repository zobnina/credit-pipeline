package ru.neoflex.trainingcenter.msdeal.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.neoflex.trainingcenter.msdeal.model.EmploymentStatus;
import ru.neoflex.trainingcenter.msdeal.model.Position;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmploymentDto {

    @NotNull
    private EmploymentStatus employmentStatus;

    @Pattern(regexp = "\\d{12}")
    @JsonProperty("employerINN")
    private String employerInn;

    @NotNull
    @Positive
    private BigDecimal salary;

    @NotNull
    private Position position;

    @NotNull
    @Min(12)
    private Integer workExperienceTotal;

    @NotNull
    @Min(3)
    private Integer workExperienceCurrent;
}
