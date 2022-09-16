package ru.neoflex.trainingcenter.msconveyor.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.neoflex.trainingcenter.msconveyor.dto.enums.Gender;
import ru.neoflex.trainingcenter.msconveyor.dto.enums.MaritalStatus;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Scoring Data")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScoringDataDto {

    @Schema(description = "Loan amount", required = true, example = "100000")
    @NotNull
    @DecimalMin(value = "10000")
    private BigDecimal amount;

    @Schema(description = "Loan term in months", required = true, example = "12")
    @NotNull
    @Min(6)
    private Integer term;

    @Schema(description = "Customer's firstname", example = "John")
    private String firstName;

    @Schema(description = "Customer's lastname", example = "Brown")
    private String lastName;

    @Schema(description = "Customer's patronymic")
    private String middleName;

    @Schema(description = "Customer's gender", example = "MALE")
    @NotNull
    private Gender gender;

    @Schema(description = "Customer's date of birth", example = "2000-01-01")
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthdate;

    @Schema(description = "Customer's passport series", example = "0000")
    @NotBlank
    @Pattern(regexp = "\\d{4}")
    private String passportSeries;

    @Schema(description = "Customer's passport number", example = "000000")
    @NotBlank
    @Pattern(regexp = "\\d{6}")
    private String passportNumber;

    @Schema(description = "Customer's passport issue date", example = "2000-01-01")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate passportIssueDate;

    @Schema(description = "Customer's passport issue branch")
    private String passportIssueBranch;

    @Schema(description = "Customer's marital status", example = "SINGLE")
    @NotNull
    private MaritalStatus maritalStatus;

    @Schema(description = "Customer's number of dependents", example = "0")
    @NotNull
    @PositiveOrZero
    private Integer dependentAmount;

    @Schema(description = "Customer's employment")
    @Valid
    @NotNull
    private EmploymentDto employment;

    @Schema(description = "Customer's account number", example = "12345678900987654321")
    @Pattern(regexp = "\\d{20}")
    private String account;

    @Schema(description = "Is insurance enabled", example = "true")
    @NotNull
    private Boolean isInsuranceEnabled;

    @Schema(description = "Is salary client", example = "true")
    @NotNull
    private Boolean isSalaryClient;
}
