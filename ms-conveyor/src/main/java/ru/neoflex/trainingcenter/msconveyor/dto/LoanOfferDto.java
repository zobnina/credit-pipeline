package ru.neoflex.trainingcenter.msconveyor.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "Loan Offer")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoanOfferDto {

    @Schema(description = "Requested loan amount", example = "10000")
    private BigDecimal requestAmount;

    @Schema(description = "Total loan amount", example = "10000")
    private BigDecimal totalAmount;

    @Schema(description = "Loan term in months", example = "12")
    private Integer term;

    @Schema(description = "Monthly payment amount", example = "1000")
    private BigDecimal monthlyPayment;

    @Schema(description = "Loan rate", example = "10")
    private BigDecimal rate;

    @Schema(description = "Is insurance enabled", example = "true")
    private Boolean isInsuranceEnabled;

    @Schema(description = "Is salary client", example = "true")
    private Boolean isSalaryClient;
}
