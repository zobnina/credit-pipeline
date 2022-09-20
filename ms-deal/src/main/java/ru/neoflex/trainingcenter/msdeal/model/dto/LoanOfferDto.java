package ru.neoflex.trainingcenter.msdeal.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "Loan Offer")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoanOfferDto implements Serializable {

    @Schema(description = "Application Id", example = "34acf27c-e09d-47f8-812e-fe41160e0605")
    @NotNull
    private UUID applicationId;

    @Schema(description = "Requested loan amount", example = "10000")
    @NotNull
    private BigDecimal requestAmount;

    @Schema(description = "Total loan amount", example = "10000")
    @NotNull
    private BigDecimal totalAmount;

    @Schema(description = "Loan term in months", example = "12")
    @NotNull
    private Integer term;

    @Schema(description = "Monthly payment amount", example = "1000")
    @NotNull
    private BigDecimal monthlyPayment;

    @Schema(description = "Loan rate", example = "10")
    @NotNull
    private BigDecimal rate;

    @Schema(description = "Is insurance enabled", example = "true")
    @NotNull
    private Boolean isInsuranceEnabled;

    @Schema(description = "Is salary client", example = "true")
    @NotNull
    private Boolean isSalaryClient;
}
