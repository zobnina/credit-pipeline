package ru.neoflex.trainingcenter.msdeal.test.controller;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.neoflex.trainingcenter.msdeal.model.ApplicationStatus;
import ru.neoflex.trainingcenter.msdeal.model.Employment;
import ru.neoflex.trainingcenter.msdeal.model.EmploymentStatus;
import ru.neoflex.trainingcenter.msdeal.model.Gender;
import ru.neoflex.trainingcenter.msdeal.model.MaritalStatus;
import ru.neoflex.trainingcenter.msdeal.model.Position;
import ru.neoflex.trainingcenter.msdeal.model.dto.EmploymentDto;
import ru.neoflex.trainingcenter.msdeal.model.dto.FinishRegistrationRequestDto;
import ru.neoflex.trainingcenter.msdeal.model.dto.LoanApplicationRequestDto;
import ru.neoflex.trainingcenter.msdeal.model.dto.LoanOfferDto;
import ru.neoflex.trainingcenter.msdeal.model.entity.Application;
import ru.neoflex.trainingcenter.msdeal.model.entity.Client;
import ru.neoflex.trainingcenter.msdeal.model.entity.Passport;
import ru.neoflex.trainingcenter.msdeal.test.DatabaseContainer;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWireMock
class DealControllerTest extends DatabaseContainer {

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void reset() {

        WireMock.reset();
    }

    @Test
    @DisplayName("POST /deal/application - 200 OK")
    void applicationTest() throws Exception {

        LoanApplicationRequestDto loanApplicationRequestDto = getLoanApplicationRequestDto();
        String request = jsonMapper.writeValueAsString(loanApplicationRequestDto);

        String responseMock = new String(Files.readAllBytes(
                new ClassPathResource("json/loanOfferDtoList.json").getFile().toPath()));

        WireMock.stubFor(WireMock.post(WireMock.urlEqualTo("/conveyor/offers"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(responseMock)));

        mockMvc.perform(post("/deal/application")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /deal/application - 400 Bad Request")
    void applicationBadRequestTest() throws Exception {

        LoanApplicationRequestDto loanApplicationRequestDto = LoanApplicationRequestDto.builder().build();
        String request = jsonMapper.writeValueAsString(loanApplicationRequestDto);

        mockMvc.perform(post("/deal/application")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /deal/offer - 404 Not Found")
    void offerNotFoundTest() throws Exception {

        String request = new String(Files.readAllBytes(
                new ClassPathResource("json/loanOfferDto.json").getFile().toPath()));

        mockMvc.perform(put("/deal/offer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /deal/offer - 400 Bad Request")
    void offerBadRequestTest() throws Exception {

        LoanOfferDto loanOfferDto = LoanOfferDto.builder().build();
        String request = jsonMapper.writeValueAsString(loanOfferDto);

        mockMvc.perform(put("/deal/offer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /deal/offer - 200 OK")
    void offerTest() throws Exception {

        Passport passport = getPassport();
        Employment employment = getEmployment();
        Client client = getClient(employment, passport);
        Application application = applicationRepository.save(Application.builder()
                .client(client)
                .status(ApplicationStatus.PREAPPROVAL)
                .creationDate(LocalDate.now())
                .build());
        String request = new String(Files.readAllBytes(
                new ClassPathResource("json/loanOfferDto.json").getFile().toPath()));
        LoanOfferDto loanOfferDto = jsonMapper.readValue(request, LoanOfferDto.class);
        loanOfferDto.setApplicationId(application.getId());

        mockMvc.perform(put("/deal/offer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(loanOfferDto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PUT /deal/calculate - 400 Bad Request")
    void calculateBadRequestTest() throws Exception {

        FinishRegistrationRequestDto finishRegistrationRequestDto = FinishRegistrationRequestDto.builder().build();

        mockMvc.perform(put("/deal/calculate/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(finishRegistrationRequestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /deal/calculate - 404 Not Found")
    void calculateNotFoundTest() throws Exception {

        FinishRegistrationRequestDto finishRegistrationRequestDto = FinishRegistrationRequestDto.builder()
                .account(faker.number().digits(20))
                .dependentAmount(0)
                .employment(EmploymentDto.builder()
                        .employerInn(faker.number().digits(12))
                        .employmentStatus(EmploymentStatus.EMPLOYED)
                        .position(Position.WORKER)
                        .salary(BigDecimal.valueOf(faker.number().numberBetween(20000, 60000)))
                        .workExperienceCurrent(faker.number().numberBetween(12, 36))
                        .workExperienceTotal(faker.number().numberBetween(36, 48))
                        .build())
                .gender(Gender.MALE)
                .maritalStatus(MaritalStatus.SINGLE)
                .passportIssueBranch(faker.address().fullAddress())
                .passportIssueDate(faker.date().past(365, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .build();

        mockMvc.perform(put("/deal/calculate/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(finishRegistrationRequestDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /deal/calculate - 422 Unprocessable Entity (ScoringException)")
    void calculateUnprocessableEntityTest() throws Exception {

        Passport passport = getPassport();
        Employment employment = getEmployment();
        Client client = getClient(employment, passport);
        Application application = applicationRepository.save(Application.builder()
                .client(client)
                .status(ApplicationStatus.PREAPPROVAL)
                .creationDate(LocalDate.now())
                .build());

        FinishRegistrationRequestDto finishRegistrationRequestDto = FinishRegistrationRequestDto.builder()
                .account(faker.number().digits(20))
                .dependentAmount(0)
                .employment(EmploymentDto.builder()
                        .employerInn(faker.number().digits(12))
                        .employmentStatus(EmploymentStatus.UNEMPLOYED)
                        .position(Position.WORKER)
                        .salary(BigDecimal.valueOf(faker.number().numberBetween(20000, 60000)))
                        .workExperienceCurrent(faker.number().numberBetween(12, 36))
                        .workExperienceTotal(faker.number().numberBetween(36, 48))
                        .build())
                .gender(Gender.MALE)
                .maritalStatus(MaritalStatus.SINGLE)
                .passportIssueBranch(faker.address().fullAddress())
                .passportIssueDate(faker.date().past(365, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .build();

        String responseMock = new String(Files.readAllBytes(
                new ClassPathResource("json/unprocessableEntity.json").getFile().toPath()));

        WireMock.stubFor(WireMock.post(WireMock.urlEqualTo("/conveyor/calculation"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.UNPROCESSABLE_ENTITY.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(responseMock)));

        mockMvc.perform(put("/deal/calculate/" + application.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(finishRegistrationRequestDto)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @DisplayName("PUT /deal/calculate - 200 OK")
    void calculateTest() throws Exception {

        Passport passport = getPassport();
        Employment employment = getEmployment();
        Client client = getClient(employment, passport);
        Application application = applicationRepository.save(Application.builder()
                .client(client)
                .status(ApplicationStatus.PREAPPROVAL)
                .creationDate(LocalDate.now())
                .build());

        FinishRegistrationRequestDto finishRegistrationRequestDto = FinishRegistrationRequestDto.builder()
                .account(faker.number().digits(20))
                .dependentAmount(0)
                .employment(EmploymentDto.builder()
                        .employerInn(faker.number().digits(12))
                        .employmentStatus(EmploymentStatus.EMPLOYED)
                        .position(Position.WORKER)
                        .salary(BigDecimal.valueOf(faker.number().numberBetween(20000, 60000)))
                        .workExperienceCurrent(faker.number().numberBetween(12, 36))
                        .workExperienceTotal(faker.number().numberBetween(36, 48))
                        .build())
                .gender(Gender.MALE)
                .maritalStatus(MaritalStatus.SINGLE)
                .passportIssueBranch(faker.address().fullAddress())
                .passportIssueDate(faker.date().past(365, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .build();

        String responseMock = new String(Files.readAllBytes(
                new ClassPathResource("json/creditDto.json").getFile().toPath()));

        WireMock.stubFor(WireMock.post(WireMock.urlEqualTo("/conveyor/calculation"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(responseMock)));

        mockMvc.perform(put("/deal/calculate/" + application.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(finishRegistrationRequestDto)))
                .andExpect(status().isOk());
    }
}
