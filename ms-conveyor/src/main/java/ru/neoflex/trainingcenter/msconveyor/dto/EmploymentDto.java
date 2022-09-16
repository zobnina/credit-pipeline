package ru.neoflex.trainingcenter.msconveyor.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.neoflex.trainingcenter.msconveyor.dto.enums.EmploymentStatus;
import ru.neoflex.trainingcenter.msconveyor.dto.enums.Position;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Schema(description = "Employment")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmploymentDto {

    @Schema(description = "Employment status", example = "EMPLOYED")
    @NotNull
    private EmploymentStatus employmentStatus;

    @Schema(description = "Customer's individual taxpayer number", example = "123456789012")
    @Pattern(regexp = "\\d{12}")
    @JsonProperty("employerINN")
    private String employerInn;

    @Schema(description = "Customer's salary amount", example = "5000")
    @NotNull
    @Positive
    private BigDecimal salary;

    @Schema(description = "Customer's position", example = "MID_MANAGER")
    @NotNull
    private Position position;

    @Schema(description = "Customer's work experience in months", example = "36")
    @NotNull
    @Min(12)
    private Integer workExperienceTotal;

    @Schema(description = "Customer's work experience at current place", example = "12")
    @NotNull
    @Min(3)
    private Integer workExperienceCurrent;
}
