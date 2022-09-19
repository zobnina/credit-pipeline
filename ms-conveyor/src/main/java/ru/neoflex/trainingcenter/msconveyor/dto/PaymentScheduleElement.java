package ru.neoflex.trainingcenter.msconveyor.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Payment Schedule Element")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentScheduleElement {

    @Schema(description = "Payment's number", example = "1")
    private Integer number;

    @Schema(description = "Payment's date", example = "2000-01-01")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @Schema(description = "Payment's total payment", example = "10000")
    private BigDecimal totalPayment;

    @Schema(description = "Payment's interest payment", example = "1000")
    private BigDecimal interestPayment;

    @Schema(description = "Payment's debt payment", example = "9000")
    private BigDecimal debtPayment;

    @Schema(description = "Remaining debt", example = "90000")
    private BigDecimal remainingDebt;
}
