package ru.neoflex.trainingcenter.msconveyor.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.neoflex.trainingcenter.liblog.DebugLog;
import ru.neoflex.trainingcenter.msconveyor.config.ConveyorConfigParam;
import ru.neoflex.trainingcenter.msconveyor.dto.CreditDto;
import ru.neoflex.trainingcenter.msconveyor.dto.LoanApplicationRequestDto;
import ru.neoflex.trainingcenter.msconveyor.dto.LoanOfferDto;
import ru.neoflex.trainingcenter.msconveyor.dto.PaymentScheduleElement;
import ru.neoflex.trainingcenter.msconveyor.dto.ScoringDataDto;
import ru.neoflex.trainingcenter.msconveyor.dto.enums.EmploymentStatus;
import ru.neoflex.trainingcenter.msconveyor.dto.enums.Gender;
import ru.neoflex.trainingcenter.msconveyor.dto.enums.MaritalStatus;
import ru.neoflex.trainingcenter.msconveyor.dto.enums.Position;
import ru.neoflex.trainingcenter.msconveyor.exception.ScoringException;
import ru.neoflex.trainingcenter.msconveyor.exception.ScoringExceptionReason;
import ru.neoflex.trainingcenter.msconveyor.service.ConveyorService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static ru.neoflex.trainingcenter.msconveyor.config.Constant.BUSINESS_OWNER_RATE_INCREASE;
import static ru.neoflex.trainingcenter.msconveyor.config.Constant.GENDER_RATE;
import static ru.neoflex.trainingcenter.msconveyor.config.Constant.HUNDRED;
import static ru.neoflex.trainingcenter.msconveyor.config.Constant.MARRIED_RATE_DECREASE;
import static ru.neoflex.trainingcenter.msconveyor.config.Constant.MAX_AGE;
import static ru.neoflex.trainingcenter.msconveyor.config.Constant.MAX_MALE_AGE;
import static ru.neoflex.trainingcenter.msconveyor.config.Constant.MID_MANAGER_RATE_DECREASE;
import static ru.neoflex.trainingcenter.msconveyor.config.Constant.MIN_AGE;
import static ru.neoflex.trainingcenter.msconveyor.config.Constant.MIN_DEPENDENT_AMOUNT;
import static ru.neoflex.trainingcenter.msconveyor.config.Constant.MIN_FEMALE_AGE;
import static ru.neoflex.trainingcenter.msconveyor.config.Constant.MIN_MALE_AGE;
import static ru.neoflex.trainingcenter.msconveyor.config.Constant.MIN_SALARIES_COUNT;
import static ru.neoflex.trainingcenter.msconveyor.config.Constant.MONTHS_PER_YEAR;
import static ru.neoflex.trainingcenter.msconveyor.config.Constant.RATE_SCALE;
import static ru.neoflex.trainingcenter.msconveyor.config.Constant.SCALE;
import static ru.neoflex.trainingcenter.msconveyor.config.Constant.TOP_MANAGER_RATE_DECREASE;

@Slf4j
@DebugLog
@Service
@RequiredArgsConstructor
public class ConveyorServiceImpl implements ConveyorService {

    private final ConveyorConfigParam configParam;

    @Override
    public List<LoanOfferDto> offers(LoanApplicationRequestDto loanApplicationRequestDto) {

        return Stream.of(createLoanOfferDto(loanApplicationRequestDto, true, true),
                        createLoanOfferDto(loanApplicationRequestDto, true, false),
                        createLoanOfferDto(loanApplicationRequestDto, false, true),
                        createLoanOfferDto(loanApplicationRequestDto, false, false))
                .sorted(Comparator.comparing(LoanOfferDto::getRate))
                .toList();
    }

    @Override
    public CreditDto calculation(ScoringDataDto scoringDataDto) {

        final BigDecimal rate = calcRate(scoringDataDto.getAmount(), scoringDataDto.getTerm(),
                scoringDataDto.getIsInsuranceEnabled(), scoringDataDto.getIsSalaryClient());
        log.debug("calculation(): rate = {}", rate);
        final BigDecimal scoring = scoring(rate, scoringDataDto);
        log.debug("calculation(): scoring = {}", scoring);
        final BigDecimal monthlyPayment = calcMonthPayment(
                scoringDataDto.getAmount(), scoring, scoringDataDto.getTerm());
        log.debug("calculation(): monthlyPayment = {}", monthlyPayment);
        final BigDecimal psk = calcPsk(scoringDataDto.getAmount(), monthlyPayment, scoringDataDto.getTerm(),
                scoringDataDto.getIsInsuranceEnabled(), scoringDataDto.getIsSalaryClient());
        log.debug("calculation(): psk = {}", psk);
        final List<PaymentScheduleElement> paymentSchedule = createPaymentSchedule(scoringDataDto.getAmount(),
                scoringDataDto.getTerm(), scoring, monthlyPayment);
        log.debug("calculation(): paymentSchedule = {}", paymentSchedule);

        return CreditDto.builder()
                .rate(scoring)
                .monthlyPayment(monthlyPayment)
                .psk(psk)
                .term(scoringDataDto.getTerm())
                .amount(scoringDataDto.getAmount())
                .isInsuranceEnabled(scoringDataDto.getIsInsuranceEnabled())
                .isSalaryClient(scoringDataDto.getIsSalaryClient())
                .paymentSchedule(paymentSchedule)
                .build();
    }

    private BigDecimal scoring(BigDecimal rate, ScoringDataDto scoringDataDto) {

        scoringAmount(scoringDataDto.getAmount(), scoringDataDto.getEmployment().getSalary());
        scoringAge(scoringDataDto.getBirthdate());
        rate = scoringEmploymentStatus(rate, scoringDataDto.getEmployment().getEmploymentStatus());
        rate = scoringPosition(rate, scoringDataDto.getEmployment().getPosition());
        rate = scoringMaritalStatus(rate, scoringDataDto.getMaritalStatus());
        rate = scoringDependentAmount(rate, scoringDataDto.getDependentAmount());
        rate = scoringGenderAge(rate, scoringDataDto.getGender(), scoringDataDto.getBirthdate());

        return rate.setScale(RATE_SCALE, RoundingMode.CEILING);
    }

    private BigDecimal scoringGenderAge(BigDecimal rate, Gender gender, LocalDate birthdate) {

        final int age = calcAge(birthdate);
        if (Gender.NON_BINARY.equals(gender)) {

            return rate.add(BigDecimal.valueOf(GENDER_RATE));
        }
        if (Gender.FEMALE.equals(gender) && (age >= MIN_FEMALE_AGE && age <= MAX_AGE)) {

            return rate.subtract(BigDecimal.valueOf(GENDER_RATE));
        }
        if (Gender.MALE.equals(gender) && (age >= MIN_MALE_AGE && age <= MAX_MALE_AGE)) {

            return rate.subtract(BigDecimal.valueOf(GENDER_RATE));
        }

        return rate;
    }

    private BigDecimal scoringDependentAmount(BigDecimal rate, Integer dependentAmount) {

        return dependentAmount > MIN_DEPENDENT_AMOUNT ? rate.add(BigDecimal.ONE) : rate;
    }

    private BigDecimal scoringMaritalStatus(BigDecimal rate, MaritalStatus maritalStatus) {

        return switch (maritalStatus) {
            case MARRIED -> rate.subtract(BigDecimal.valueOf(MARRIED_RATE_DECREASE));
            case DIVORCED -> rate.add(BigDecimal.ONE);
            default -> rate;
        };
    }

    private BigDecimal scoringPosition(BigDecimal rate, Position position) {

        return switch (position) {
            case MID_MANAGER -> rate.subtract(BigDecimal.valueOf(MID_MANAGER_RATE_DECREASE));
            case TOP_MANAGER -> rate.subtract(BigDecimal.valueOf(TOP_MANAGER_RATE_DECREASE));
            default -> rate;
        };
    }

    private BigDecimal scoringEmploymentStatus(BigDecimal rate, EmploymentStatus employmentStatus) {

        return switch (employmentStatus) {
            case UNEMPLOYED -> throw new ScoringException(ScoringExceptionReason.UNEMPLOYED_STATUS);
            case SELF_EMPLOYED -> rate.add(BigDecimal.ONE);
            case BUSINESS_OWNER -> rate.add(BigDecimal.valueOf(BUSINESS_OWNER_RATE_INCREASE));
            default -> rate;
        };
    }

    private void scoringAge(LocalDate birthdate) {

        final int age = calcAge(birthdate);
        if (age < MIN_AGE || age > MAX_AGE) {

            throw new ScoringException(ScoringExceptionReason.INAPPROPRIATE_AGE);
        }
    }

    private int calcAge(LocalDate birthdate) {

        return Period.between(birthdate, LocalDate.now()).getYears();
    }

    private void scoringAmount(BigDecimal amount, BigDecimal salary) {

        if (amount.compareTo(salary.multiply(BigDecimal.valueOf(MIN_SALARIES_COUNT))) > 0) {

            throw new ScoringException(ScoringExceptionReason.AMOUNT_TOO_BIG);
        }
    }

    private List<PaymentScheduleElement> createPaymentSchedule(BigDecimal amount,
                                                               Integer term,
                                                               BigDecimal rate,
                                                               BigDecimal monthlyPayment) {

        final List<PaymentScheduleElement> paymentScheduleElements = new ArrayList<>(term);
        paymentScheduleElements.add(zeroPayment(amount));

        IntStream.range(1, term + 1)
                .mapToObj(i -> Map.entry(i, paymentScheduleElements.get(i - 1)))
                .forEach(entry -> {
                    PaymentScheduleElement previous = entry.getValue();
                    final LocalDate date = previous.getDate().plusMonths(1);
                    final BigDecimal interestPayment = interestPayment(previous.getRemainingDebt(), rate);
                    final BigDecimal totalPayment = totalPayment(previous.getRemainingDebt(),
                            monthlyPayment, interestPayment);
                    final BigDecimal debtPayment = totalPayment.subtract(interestPayment);
                    final BigDecimal remainingDebt = previous.getRemainingDebt().subtract(debtPayment);

                    paymentScheduleElements.add(PaymentScheduleElement.builder()
                            .number(entry.getKey())
                            .date(date)
                            .interestPayment(interestPayment)
                            .totalPayment(totalPayment)
                            .debtPayment(debtPayment)
                            .remainingDebt(remainingDebt)
                            .build());
                });

        return paymentScheduleElements;
    }

    private BigDecimal interestPayment(BigDecimal remainingDebt, BigDecimal rate) {

        return remainingDebt.multiply(rateMonth(rate)).setScale(SCALE, RoundingMode.CEILING);
    }

    private BigDecimal totalPayment(BigDecimal remainingDebt, BigDecimal monthlyPayment, BigDecimal interestPayment) {

        if (remainingDebt.compareTo(monthlyPayment) > 0) {

            return monthlyPayment;
        }

        return remainingDebt.add(interestPayment);
    }

    private PaymentScheduleElement zeroPayment(BigDecimal amount) {

        return PaymentScheduleElement.builder()
                .number(0)
                .date(LocalDate.now())
                .totalPayment(BigDecimal.ZERO)
                .debtPayment(BigDecimal.ZERO)
                .interestPayment(BigDecimal.ZERO)
                .remainingDebt(amount)
                .build();
    }

    private LoanOfferDto createLoanOfferDto(LoanApplicationRequestDto loanApplicationRequestDto,
                                            boolean isInsuranceEnabled,
                                            boolean isSalaryClient) {

        final BigDecimal rate = calcRate(loanApplicationRequestDto.getAmount(),
                loanApplicationRequestDto.getTerm(), isInsuranceEnabled, isSalaryClient);
        log.debug("createLoanOfferDto(): rate = {}", rate);
        final BigDecimal monthlyPayment = calcMonthPayment(loanApplicationRequestDto.getAmount(),
                rate, loanApplicationRequestDto.getTerm());
        log.debug("createLoanOfferDto(): monthlyPayment = {}", monthlyPayment);
        final BigDecimal totalAmount = calcPsk(loanApplicationRequestDto.getAmount(),
                monthlyPayment, loanApplicationRequestDto.getTerm(), isInsuranceEnabled, isSalaryClient);
        log.debug("createLoanOfferDto(): totalAmount = {}", totalAmount);

        return LoanOfferDto.builder()
                .isInsuranceEnabled(isInsuranceEnabled)
                .isSalaryClient(isSalaryClient)
                .requestAmount(loanApplicationRequestDto.getAmount())
                .term(loanApplicationRequestDto.getTerm())
                .rate(rate)
                .monthlyPayment(monthlyPayment)
                .totalAmount(totalAmount)
                .build();
    }

    private BigDecimal calcPsk(BigDecimal amount,
                               BigDecimal monthlyPayment,
                               Integer term,
                               boolean isInsuranceEnabled,
                               boolean isSalaryClient) {

        final BigDecimal psk = monthlyPayment.multiply(BigDecimal.valueOf(term));
        if (Boolean.TRUE.equals(isInsuranceEnabled)) {
            double yearCount = (double) term / MONTHS_PER_YEAR;
            BigDecimal insuranceCostTotal = insuranceCost(amount, isSalaryClient)
                    .multiply(BigDecimal.valueOf(yearCount));

            return psk.add(insuranceCostTotal);
        }

        return psk;
    }

    private BigDecimal insuranceCost(BigDecimal amount, boolean isSalaryClient) {

        if (Boolean.TRUE.equals(isSalaryClient)) {

            return amount.multiply(configParam.getInsurance().getSalaryClientPercent())
                    .divide(HUNDRED, SCALE, RoundingMode.CEILING);
        }

        return amount.multiply(configParam.getInsurance().getPercent())
                .divide(HUNDRED, SCALE, RoundingMode.CEILING);
    }

    private BigDecimal calcMonthPayment(BigDecimal amount, BigDecimal rate, Integer term) {

        final BigDecimal pm = rateMonth(rate);
        final BigDecimal onePlusPm = BigDecimal.ONE.add(pm);
        final BigDecimal onePlusPmPow = onePlusPm.pow(term);
        final BigDecimal powMinusOne = onePlusPmPow.subtract(BigDecimal.ONE);
        final BigDecimal pmDiv = pm.divide(powMinusOne, SCALE, RoundingMode.CEILING);
        final BigDecimal pmPlusFraction = pm.add(pmDiv);

        return amount.multiply(pmPlusFraction).setScale(SCALE, RoundingMode.CEILING);
    }

    private BigDecimal rateMonth(BigDecimal rate) {

        return rate.divide(HUNDRED, SCALE, RoundingMode.CEILING)
                .divide(BigDecimal.valueOf(MONTHS_PER_YEAR), SCALE, RoundingMode.CEILING);
    }

    private BigDecimal calcRate(BigDecimal amount, Integer term, boolean isInsuranceEnabled, boolean isSalaryClient) {

        BigDecimal rate = configParam.getRate().getBase();
        rate = calcRateByAmount(rate, amount);
        rate = calcRateByTerm(rate, term);
        rate = calcByInsurance(rate, isInsuranceEnabled);
        rate = calcBySalaryClient(rate, isSalaryClient);

        return rate.setScale(RATE_SCALE, RoundingMode.CEILING);
    }

    private BigDecimal calcBySalaryClient(BigDecimal rate, boolean isSalaryClient) {

        if (Boolean.TRUE.equals(isSalaryClient)) {

            return rate.multiply(HUNDRED.subtract(configParam.getRate().getSalaryClient())
                    .divide(HUNDRED, SCALE, RoundingMode.CEILING));
        }

        return rate;
    }

    private BigDecimal calcByInsurance(BigDecimal rate, boolean isInsuranceEnabled) {

        if (Boolean.TRUE.equals(isInsuranceEnabled)) {

            return rate.multiply(HUNDRED.subtract(configParam.getRate().getInsurance())
                    .divide(HUNDRED, SCALE, RoundingMode.CEILING));
        }

        return rate;
    }

    private BigDecimal calcRateByTerm(BigDecimal rate, Integer term) {

        final boolean isSmallTerm = term <= configParam.getTerm().getMiddle();
        final boolean isMiddleTerm = !isSmallTerm && term <= configParam.getTerm().getBig();

        if (isSmallTerm) {

            return rate.add(rate.multiply(configParam.getRate().getTerm().getSmall()
                    .divide(HUNDRED, SCALE, RoundingMode.CEILING)));
        }
        if (isMiddleTerm) {

            return rate.add(rate.multiply(configParam.getRate().getTerm().getMiddle()
                    .divide(HUNDRED, SCALE, RoundingMode.CEILING)));
        }

        return rate.add(rate.multiply(configParam.getRate().getTerm().getBig()
                .divide(HUNDRED, SCALE, RoundingMode.CEILING)));
    }

    private BigDecimal calcRateByAmount(BigDecimal rate, BigDecimal amount) {

        final boolean isSmallAmount = amount.compareTo(configParam.getAmount().getMiddle()) < 0;
        final boolean isMiddleAmount = !isSmallAmount && amount.compareTo(configParam.getAmount().getBig()) < 0;

        if (isSmallAmount) {

            return rate.add(rate.multiply(configParam.getRate().getAmount().getSmall()
                    .divide(HUNDRED, SCALE, RoundingMode.CEILING)));
        }
        if (isMiddleAmount) {

            return rate.add(rate.multiply(configParam.getRate().getAmount().getMiddle()
                    .divide(HUNDRED, SCALE, RoundingMode.CEILING)));
        }

        return rate.add(rate.multiply(configParam.getRate().getAmount().getBig()
                .divide(HUNDRED, SCALE, RoundingMode.CEILING)));
    }
}
