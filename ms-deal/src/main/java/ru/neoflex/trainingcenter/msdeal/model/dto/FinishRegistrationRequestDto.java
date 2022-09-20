package ru.neoflex.trainingcenter.msdeal.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.neoflex.trainingcenter.msdeal.model.Gender;
import ru.neoflex.trainingcenter.msdeal.model.MaritalStatus;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

@Schema(description = "Finish Registration Request")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FinishRegistrationRequestDto {

    @Schema(description = "Customer's gender", example = "MALE")
    @NotNull
    private Gender gender;

    @Schema(description = "Customer's marital status", example = "SINGLE")
    @NotNull
    private MaritalStatus maritalStatus;

    @Schema(description = "Customer's number of dependents", example = "0")
    @NotNull
    @PositiveOrZero
    private Integer dependentAmount;

    @Schema(description = "Customer's passport issue date", example = "2000-01-01")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate passportIssueDate;

    @Schema(description = "Customer's passport issue branch")
    @NotBlank
    private String passportIssueBranch;

    @Schema(description = "Customer's employment")
    @Valid
    @NotNull
    private EmploymentDto employment;

    @Schema(description = "Customer's account number", example = "12345678900987654321")
    @Pattern(regexp = "\\d{20}")
    private String account;
}
