package ru.neoflex.trainingcenter.msdeal.test.mapper;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.neoflex.trainingcenter.msdeal.mapper.ApplicationMapper;
import ru.neoflex.trainingcenter.msdeal.mapper.ApplicationStatusHistoryMapper;
import ru.neoflex.trainingcenter.msdeal.mapper.ClientMapper;
import ru.neoflex.trainingcenter.msdeal.mapper.CreditMapper;
import ru.neoflex.trainingcenter.msdeal.model.ApplicationStatus;
import ru.neoflex.trainingcenter.msdeal.model.CreditStatus;
import ru.neoflex.trainingcenter.msdeal.model.dto.CreditDto;
import ru.neoflex.trainingcenter.msdeal.model.dto.LoanApplicationRequestDto;
import ru.neoflex.trainingcenter.msdeal.model.dto.LoanOfferDto;
import ru.neoflex.trainingcenter.msdeal.model.entity.Application;
import ru.neoflex.trainingcenter.msdeal.model.entity.ApplicationStatusHistory;
import ru.neoflex.trainingcenter.msdeal.model.entity.Client;
import ru.neoflex.trainingcenter.msdeal.model.entity.Credit;
import ru.neoflex.trainingcenter.msdeal.model.entity.Passport;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class MapperTest {

    final Faker faker = new Faker();

    @Test
    @DisplayName("Проверка createApplication()")
    void createApplicationTest() {

        Client client = Client.builder().build();
        Application application = ApplicationMapper.MAPPER.createApplication(client);

        assertAll(
                () -> assertNotNull(application),
                () -> assertEquals(client, application.getClient()),
                () -> assertEquals(ApplicationStatus.PREAPPROVAL, application.getStatus()),
                () -> assertEquals(LocalDate.now(), application.getCreationDate())
        );
    }

    @Test
    @DisplayName("Проверка applicationToApplicationHistory()")
    void applicationToApplicationHistoryTest() {

        Application application = getApplication();
        ApplicationStatusHistory applicationStatusHistory =
                ApplicationStatusHistoryMapper.MAPPER.applicationToApplicationHistory(application);

        assertAll(
                () -> assertNotNull(applicationStatusHistory),
                () -> assertEquals(application.getStatus(), applicationStatusHistory.getStatus()),
                () -> assertEquals(LocalDate.now(), applicationStatusHistory.getTime().toLocalDate()),
                () -> assertNull(applicationStatusHistory.getChangeType())
        );
    }

    @Test
    @DisplayName("Проверка applicationStatusHistories()")
    void applicationStatusHistoriesTest() {

        Application application = getApplication();
        List<ApplicationStatusHistory> applicationStatusHistories =
                ApplicationStatusHistoryMapper.MAPPER.applicationStatusHistories(application);

        assertAll(
                () -> assertNotNull(applicationStatusHistories),
                () -> assertEquals(1, applicationStatusHistories.size())
        );
    }

    @Test
    @DisplayName("Проверка loanApplicationRequestDtoToClient()")
    void loanApplicationRequestDtoToClientTest() {

        LoanApplicationRequestDto loanApplicationRequestDto = getLoanApplicationRequestDto();
        Passport passport = getPassport();
        Client client = ClientMapper.MAPPER.createClient(loanApplicationRequestDto, passport);

        assertAll(
                () -> assertNotNull(client),
                () -> assertNull(client.getId()),
                () -> assertNull(client.getGender()),
                () -> assertNull(client.getMaritalStatus()),
                () -> assertNull(client.getDependentAmount()),
                () -> assertNull(client.getEmployment()),
                () -> assertNull(client.getAccount()),
                () -> assertNull(client.getApplications()),
                () -> assertEquals(loanApplicationRequestDto.getLastName(), client.getLastName()),
                () -> assertEquals(loanApplicationRequestDto.getFirstName(), client.getFirstName()),
                () -> assertEquals(loanApplicationRequestDto.getMiddleName(), client.getMiddleName()),
                () -> assertEquals(loanApplicationRequestDto.getBirthdate(), client.getBirthDate()),
                () -> assertEquals(loanApplicationRequestDto.getEmail(), client.getEmail()),
                () -> assertEquals(passport, client.getPassport())
        );
    }

    @Test
    @DisplayName("Проверка creditDtoToCredit()")
    void creditDtoToCreditTest() {

        CreditDto creditDto = CreditDto.builder()
                .amount(BigDecimal.TEN)
                .isInsuranceEnabled(faker.bool().bool())
                .isSalaryClient(faker.bool().bool())
                .monthlyPayment(BigDecimal.TEN)
                .paymentSchedule(Collections.emptyList())
                .psk(BigDecimal.TEN)
                .rate(BigDecimal.TEN)
                .term(faker.number().randomDigit())
                .build();
        Credit credit = CreditMapper.MAPPER.creditDtoToCredit(creditDto);

        assertAll(
                () -> assertNotNull(credit),
                () -> assertNull(credit.getId()),
                () -> assertEquals(CreditStatus.CALCULATED, credit.getCreditStatus()),
                () -> assertEquals(creditDto.getAmount(), credit.getAmount()),
                () -> assertEquals(creditDto.getIsInsuranceEnabled(), credit.getIsInsuranceEnabled()),
                () -> assertEquals(creditDto.getIsSalaryClient(), credit.getIsSalaryClient()),
                () -> assertEquals(creditDto.getMonthlyPayment(), credit.getMonthlyPayment()),
                () -> assertEquals(creditDto.getPaymentSchedule(), credit.getPaymentSchedule()),
                () -> assertEquals(creditDto.getPsk(), credit.getPsk()),
                () -> assertEquals(creditDto.getRate(), credit.getRate()),
                () -> assertEquals(creditDto.getTerm(), credit.getTerm())
        );
    }

    private Passport getPassport() {

        return Passport.builder()
                .series(faker.number().digits(4))
                .number(faker.number().digits(6))
                .issueBranch(faker.address().fullAddress())
                .issueDate(faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .build();
    }

    private LoanApplicationRequestDto getLoanApplicationRequestDto() {

        return LoanApplicationRequestDto.builder()
                .amount(BigDecimal.TEN)
                .birthdate(faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .email(faker.internet().emailAddress())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .middleName(faker.name().nameWithMiddle())
                .passportNumber(faker.number().digits(6))
                .passportSeries(faker.number().digits(4))
                .term(faker.number().randomDigit())
                .build();
    }

    private Application getApplication() {

        return Application.builder()
                .creationDate(LocalDate.now())
                .appliedOffer(LoanOfferDto.builder().build())
                .client(Client.builder().build())
                .id(UUID.randomUUID())
                .status(ApplicationStatus.PREAPPROVAL)
                .credit(Credit.builder().build())
                .sesCode(faker.lorem().word())
                .statusHistory(Collections.emptyList())
                .signDate(LocalDate.now())
                .build();
    }
}
