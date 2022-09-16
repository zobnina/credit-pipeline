package ru.neoflex.trainingcenter.msconveyor.test;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.neoflex.trainingcenter.msconveyor.dto.CreditDto;
import ru.neoflex.trainingcenter.msconveyor.dto.EmploymentDto;
import ru.neoflex.trainingcenter.msconveyor.dto.LoanApplicationRequestDto;
import ru.neoflex.trainingcenter.msconveyor.dto.ScoringDataDto;
import ru.neoflex.trainingcenter.msconveyor.dto.enums.EmploymentStatus;
import ru.neoflex.trainingcenter.msconveyor.dto.enums.Gender;
import ru.neoflex.trainingcenter.msconveyor.dto.enums.MaritalStatus;
import ru.neoflex.trainingcenter.msconveyor.dto.enums.Position;


import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.ZoneId;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ConveyorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final Faker faker = new Faker();
    private final JsonMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();

    @Test
    @DisplayName("Checking POST /offers - 200 OK")
    void offersTest() throws Exception {

        LoanApplicationRequestDto loanApplicationRequestDTO = LoanApplicationRequestDto.builder()
                .amount(BigDecimal.valueOf(100000))
                .term(12)
                .birthdate(faker.date().birthday(30, 55).toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .build();
        String request = jsonMapper.writeValueAsString(loanApplicationRequestDTO);
        String expectedResponse = new String(Files.readAllBytes(
                new ClassPathResource("json/offers_200.json").getFile().toPath()));

        MvcResult mvcResult = mockMvc.perform(post("/conveyor/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andReturn();
        String actualResponse = mvcResult.getResponse().getContentAsString();

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Checking POST /offers - 400 Bad Request")
    void offersBadRequestTest() throws Exception {

        LoanApplicationRequestDto loanApplicationRequestDTO = LoanApplicationRequestDto.builder()
                .amount(BigDecimal.valueOf(100000))
                .term(12)
                .build();
        String request = jsonMapper.writeValueAsString(loanApplicationRequestDTO);

        mockMvc.perform(post("/conveyor/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @DisplayName("Checking POST /calculation - 200 OK")
    void calculationTest() throws Exception {

        ScoringDataDto scoringDataDTO = scoringDataDTO(employmentDTO());
        String request = jsonMapper.writeValueAsString(scoringDataDTO);

        mockMvc.perform(post("/conveyor/calculation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Checking POST /calculation - 400 Bad Request")
    void calculationBadRequestTest() throws Exception {

        ScoringDataDto scoringDataDTO = scoringDataDTO(employmentDTO());
        scoringDataDTO.setAmount(null);
        String request = jsonMapper.writeValueAsString(scoringDataDTO);

        mockMvc.perform(post("/conveyor/calculation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Checking POST /calculation - 422 Unprocessable Entity")
    void calculationUnprocessableEntityTest() throws Exception {

        ScoringDataDto scoringDataDTO = scoringDataDTO(employmentDTO());
        scoringDataDTO.setAmount(BigDecimal.valueOf(100000000));
        String request = jsonMapper.writeValueAsString(scoringDataDTO);

        mockMvc.perform(post("/conveyor/calculation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isUnprocessableEntity());
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
                .passportSeries(faker.number().digits(4))
                .passportNumber(faker.number().digits(6))
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
