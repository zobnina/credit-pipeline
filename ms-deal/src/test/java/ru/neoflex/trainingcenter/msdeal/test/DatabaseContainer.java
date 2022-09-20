package ru.neoflex.trainingcenter.msdeal.test;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.github.javafaker.Faker;
import lombok.NonNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.neoflex.trainingcenter.msdeal.controller.DealController;
import ru.neoflex.trainingcenter.msdeal.controller.DocumentController;
import ru.neoflex.trainingcenter.msdeal.feign.ConveyorFeignClient;
import ru.neoflex.trainingcenter.msdeal.model.Employment;
import ru.neoflex.trainingcenter.msdeal.model.EmploymentStatus;
import ru.neoflex.trainingcenter.msdeal.model.Gender;
import ru.neoflex.trainingcenter.msdeal.model.MaritalStatus;
import ru.neoflex.trainingcenter.msdeal.model.Position;
import ru.neoflex.trainingcenter.msdeal.model.dto.LoanApplicationRequestDto;
import ru.neoflex.trainingcenter.msdeal.model.entity.Client;
import ru.neoflex.trainingcenter.msdeal.model.entity.Passport;
import ru.neoflex.trainingcenter.msdeal.repo.ApplicationRepository;
import ru.neoflex.trainingcenter.msdeal.service.DealService;
import ru.neoflex.trainingcenter.msdeal.service.DocumentService;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = DatabaseContainer.DockerPostgreDataSourceInitializer.class)
@Testcontainers
public abstract class DatabaseContainer {

    public static PostgreSQLContainer<?> postgreDBContainer = new PostgreSQLContainer<>("postgres:9.4");

    static {
        postgreDBContainer.start();
    }

    protected final Faker faker = new Faker();
    protected final JsonMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();

    @Autowired
    protected DealController dealController;

    @Autowired
    protected DocumentController documentController;

    @Autowired
    protected DealService dealService;

    @Autowired
    protected DocumentService documentService;

    @Autowired
    protected ApplicationRepository applicationRepository;

    @Autowired
    protected ConveyorFeignClient conveyorFeignClient;

    @Test
    void containerTest() {

        assertTrue(postgreDBContainer.isCreated());
        assertTrue(postgreDBContainer.isRunning());
    }

    @Test
    void contextTest() {

        assertNotNull(dealController);
        assertNotNull(documentController);
        assertNotNull(dealService);
        assertNotNull(documentService);
        assertNotNull(applicationRepository);
        assertNotNull(conveyorFeignClient);
    }

    public static class DockerPostgreDataSourceInitializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(@NonNull ConfigurableApplicationContext applicationContext) {

            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "spring.datasource.url=" + postgreDBContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreDBContainer.getUsername(),
                    "spring.datasource.password=" + postgreDBContainer.getPassword()
            );
        }
    }

    protected LoanApplicationRequestDto getLoanApplicationRequestDto() {

        return LoanApplicationRequestDto.builder()
                .amount(BigDecimal.valueOf(10000))
                .birthdate(faker.date().birthday(25, 50).toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .email(faker.internet().emailAddress())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .middleName(faker.name().nameWithMiddle())
                .passportNumber(faker.number().digits(6))
                .passportSeries(faker.number().digits(4))
                .term(6)
                .build();
    }

    protected Employment getEmployment() {

        return Employment.builder()
                .employerInn(faker.number().digits(12))
                .employmentStatus(EmploymentStatus.EMPLOYED)
                .position(Position.WORKER)
                .salary(BigDecimal.valueOf(faker.number().numberBetween(20000, 60000)))
                .workExperienceCurrent(faker.number().numberBetween(12, 36))
                .workExperienceTotal(faker.number().numberBetween(36, 50))
                .build();
    }

    protected Client getClient(Employment employment, Passport passport) {

        return Client.builder()
                .account(faker.number().digits(20))
                .birthDate(faker.date().birthday(25, 50).toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .email(faker.internet().emailAddress())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .middleName(faker.name().nameWithMiddle())
                .employment(employment)
                .passport(passport)
                .gender(Gender.MALE)
                .maritalStatus(MaritalStatus.SINGLE)
                .dependentAmount(0)
                .build();
    }

    protected Passport getPassport() {

        return Passport.builder()
                .issueBranch(faker.address().fullAddress())
                .issueDate(faker.date().past(365, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .number(faker.number().digits(6))
                .series(faker.number().digits(4))
                .build();
    }
}
