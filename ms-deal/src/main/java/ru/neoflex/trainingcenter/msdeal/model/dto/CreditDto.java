package ru.neoflex.trainingcenter.msdeal.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import ru.neoflex.trainingcenter.msdeal.model.entity.PaymentScheduleElement;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditDto {

    private BigDecimal amount;

    private Integer term;

    private BigDecimal monthlyPayment;

    private BigDecimal rate;

    private BigDecimal psk;

    private Boolean isInsuranceEnabled;

    private Boolean isSalaryClient;

    private List<PaymentScheduleElement> paymentSchedule;
}
