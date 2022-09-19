package ru.neoflex.trainingcenter.msconveyor.test;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.neoflex.trainingcenter.msconveyor.dto.CreditDto;
import ru.neoflex.trainingcenter.msconveyor.dto.EmploymentDto;
import ru.neoflex.trainingcenter.msconveyor.dto.LoanApplicationRequestDto;
import ru.neoflex.trainingcenter.msconveyor.dto.LoanOfferDto;
import ru.neoflex.trainingcenter.msconveyor.dto.PaymentScheduleElement;
import ru.neoflex.trainingcenter.msconveyor.dto.ScoringDataDto;
import ru.neoflex.trainingcenter.msconveyor.dto.enums.EmploymentStatus;
import ru.neoflex.trainingcenter.msconveyor.dto.enums.Gender;
import ru.neoflex.trainingcenter.msconveyor.dto.enums.MaritalStatus;
import ru.neoflex.trainingcenter.msconveyor.dto.enums.Position;
import ru.neoflex.trainingcenter.msconveyor.exception.ScoringException;
import ru.neoflex.trainingcenter.msconveyor.service.ConveyorService;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest
class ConveyorServiceTest {

    @Autowired
    private ConveyorService conveyorService;

    private final Faker faker = new Faker();

    @Test
    @DisplayName("Checking offers method")
    void offersTest() {

        LoanApplicationRequestDto loanApplicationRequestDTO = LoanApplicationRequestDto.builder()
                .amount(BigDecimal.valueOf(100000))
                .term(12)
                .build();

        List<LoanOfferDto> result = conveyorService.offers(loanApplicationRequestDTO);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(4, result.size())
        );
        result.forEach(loanOfferDTO -> assertAll(
                () -> assertEquals(loanApplicationRequestDTO.getAmount(), loanOfferDTO.getRequestAmount()),
                () -> assertEquals(loanApplicationRequestDTO.getTerm(), loanOfferDTO.getTerm())
        ));
        assertAll(
                () -> assertEquals(0, BigDecimal.valueOf(106392).compareTo(result.get(0).getTotalAmount())),
                () -> assertEquals(0, BigDecimal.valueOf(8866).compareTo(result.get(0).getMonthlyPayment())),
                () -> assertEquals(0, BigDecimal.valueOf(11.57).compareTo(result.get(0).getRate())),
                () -> assertTrue(result.get(0).getIsInsuranceEnabled()),
                () -> assertTrue(result.get(0).getIsSalaryClient()),

                () -> assertEquals(0, BigDecimal.valueOf(107112).compareTo(result.get(1).getTotalAmount())),
                () -> assertEquals(0, BigDecimal.valueOf(8926).compareTo(result.get(1).getMonthlyPayment())),
                () -> assertEquals(0, BigDecimal.valueOf(12.86).compareTo(result.get(1).getRate())),
                () -> assertFalse(result.get(1).getIsInsuranceEnabled()),
                () -> assertTrue(result.get(1).getIsSalaryClient()),

                () -> assertEquals(0, BigDecimal.valueOf(110268).compareTo(result.get(2).getTotalAmount())),
                () -> assertEquals(0, BigDecimal.valueOf(8939).compareTo(result.get(2).getMonthlyPayment())),
                () -> assertEquals(0, BigDecimal.valueOf(13.15).compareTo(result.get(2).getRate())),
                () -> assertTrue(result.get(2).getIsInsuranceEnabled()),
                () -> assertFalse(result.get(2).getIsSalaryClient()),

                () -> assertEquals(0, BigDecimal.valueOf(108096).compareTo(result.get(3).getTotalAmount())),
                () -> assertEquals(0, BigDecimal.valueOf(9008).compareTo(result.get(3).getMonthlyPayment())),
                () -> assertEquals(0, BigDecimal.valueOf(14.61).compareTo(result.get(3).getRate())),
                () -> assertFalse(result.get(3).getIsInsuranceEnabled()),
                () -> assertFalse(result.get(3).getIsSalaryClient())
        );
    }

    @Test
    @DisplayName("Checking the calculation method")
    void calculationTest() {

        ScoringDataDto scoringDataDTO = scoringDataDTO(employmentDTO());

        CreditDto result = conveyorService.calculation(scoringDataDTO);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(scoringDataDTO.getAmount(), result.getAmount()),
                () -> assertEquals(scoringDataDTO.getTerm(), result.getTerm()),
                () -> assertEquals(0, BigDecimal.valueOf(9008).compareTo(result.getMonthlyPayment())),
                () -> assertEquals(0, BigDecimal.valueOf(14.61).compareTo(result.getRate())),
                () -> assertEquals(0, BigDecimal.valueOf(108096).compareTo(result.getPsk())),
                () -> assertFalse(result.getIsInsuranceEnabled()),
                () -> assertFalse(result.getIsSalaryClient()),
                () -> assertEquals(scoringDataDTO.getTerm() + 1, result.getPaymentSchedule().size())
        );
        result.getPaymentSchedule().forEach(paymentScheduleElement -> assertAll(
                () -> assertNotNull(paymentScheduleElement.getNumber()),
                () -> assertNotNull(paymentScheduleElement.getDate()),
                () -> assertNotNull(paymentScheduleElement.getTotalPayment()),
                () -> assertNotNull(paymentScheduleElement.getInterestPayment()),
                () -> assertNotNull(paymentScheduleElement.getDebtPayment()),
                () -> assertNotNull(paymentScheduleElement.getRemainingDebt())
        ));
        PaymentScheduleElement last = result.getPaymentSchedule().get(result.getPaymentSchedule().size() - 1);
        assertEquals(0, BigDecimal.ZERO.compareTo(last.getRemainingDebt()));
    }

    @Test
    @DisplayName("Checking the calculation method. Scoring exception")
    void calculationExceptionTest() {

        ScoringDataDto scoringDataDTO = scoringDataDTO(employmentDTO());
        scoringDataDTO.setAmount(BigDecimal.valueOf(1000000));

        assertThrows(ScoringException.class, () -> conveyorService.calculation(scoringDataDTO));
    }

    private ScoringDataDto scoringDataDTO(EmploymentDto employmentDTO) {

        return ScoringDataDto.builder()
                .amount(BigDecimal.valueOf(100000))
                .term(12)
                .isInsuranceEnabled(false)
                .isSalaryClient(false)
                .birthdate(faker.date().birthday(25, 25).toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .employment(employmentDTO)
                .gender(Gender.MALE)
                .maritalStatus(MaritalStatus.SINGLE)
                .dependentAmount(0)
                .build();
    }

    private EmploymentDto employmentDTO() {

        return EmploymentDto.builder()
                .employmentStatus(EmploymentStatus.EMPLOYED)
                .position(Position.WORKER)
                .salary(BigDecimal.valueOf(20000))
                .workExperienceCurrent(12)
                .workExperienceTotal(36)
                .build();
    }
}
