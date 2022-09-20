package ru.neoflex.trainingcenter.msdeal.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.neoflex.trainingcenter.msdeal.model.Gender;
import ru.neoflex.trainingcenter.msdeal.model.MaritalStatus;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScoringDataDto {

    @NotNull
    @DecimalMin("10000")
    private BigDecimal amount;

    @NotNull
    @Min(6)
    private Integer term;

    private String firstName;

    private String lastName;

    private String middleName;

    @NotNull
    private Gender gender;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthdate;

    @NotBlank
    @Pattern(regexp = "\\d{4}")
    private String passportSeries;

    @NotBlank
    @Pattern(regexp = "\\d{6}")
    private String passportNumber;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate passportIssueDate;

    private String passportIssueBranch;

    @NotNull
    private MaritalStatus maritalStatus;

    @NotNull
    @PositiveOrZero
    private Integer dependentAmount;

    @Valid
    @NotNull
    private EmploymentDto employment;

    @Pattern(regexp = "\\d{20}")
    private String account;

    @NotNull
    private Boolean isInsuranceEnabled;

    @NotNull
    private Boolean isSalaryClient;
}
