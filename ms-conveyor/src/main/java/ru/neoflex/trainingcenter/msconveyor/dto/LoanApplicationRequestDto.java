package ru.neoflex.trainingcenter.msconveyor.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Loan Application Request")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoanApplicationRequestDto {

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

    @Schema(description = "Customer's email", example = "johnbrown@domain.com")
    @Email
    private String email;

    @Schema(description = "Customer's date of birth", example = "2000-01-01")
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthdate;

    @Schema(description = "Customer's passport series", example = "0000")
    @Pattern(regexp = "\\d{4}")
    private String passportSeries;

    @Schema(description = "Customer's passport number", example = "000000")
    @Pattern(regexp = "\\d{6}")
    private String passportNumber;
}
