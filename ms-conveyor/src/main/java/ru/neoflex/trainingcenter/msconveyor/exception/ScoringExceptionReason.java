package ru.neoflex.trainingcenter.msconveyor.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ScoringExceptionReason {

    UNEMPLOYED_STATUS("Unemployed status"),
    AMOUNT_TOO_BIG("Amount is greater than 20 salaries"),
    INAPPROPRIATE_AGE("Age is under 20 or greater than 60"),
    MONTHLY_PAYMENT_TOO_BIG("Monthly payment is greater than 50% of salary");

    @Getter
    private final String message;
}
