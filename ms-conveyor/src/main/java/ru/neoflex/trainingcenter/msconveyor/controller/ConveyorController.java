package ru.neoflex.trainingcenter.msconveyor.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.neoflex.trainingcenter.msconveyor.dto.CreditDto;
import ru.neoflex.trainingcenter.msconveyor.dto.LoanApplicationRequestDto;
import ru.neoflex.trainingcenter.msconveyor.dto.LoanOfferDto;
import ru.neoflex.trainingcenter.msconveyor.dto.ScoringDataDto;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Credit Conveyor Controller",
        description = "Methods of Credit Conveyor Controller")
@Validated
@RequestMapping(value = "/conveyor",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public interface ConveyorController {

    @Operation(
            method = "POST",
            summary = "Calculation of possible loan terms",
            description = "Based on the request 4 credit offers are created " +
                    "based on all possible combinations of isInsuranceEnabled and isSalaryClient " +
                    "(false-false, false-true, true-false, true-true)",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Loan Application Request",
                    content = @Content(schema = @Schema(implementation = LoanApplicationRequestDto.class)),
                    required = true),
            operationId = "offers",
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = "200",
                            content = @Content(array =
                            @ArraySchema(schema = @Schema(implementation = LoanOfferDto.class)))),
                    @ApiResponse(description = "Bad Request",
                            responseCode = "400")
            }
    )
    @PostMapping("/offers")
    ResponseEntity<List<LoanOfferDto>> offers(@RequestBody @Valid LoanApplicationRequestDto loanApplicationRequestDto);

    @Operation(
            method = "POST",
            summary = "Full calculation of loan parameters",
            description = "There is data scoring, calculation of the rate, the full cost of the loan, " +
                    "the amount of the monthly payment, the schedule of monthly payments",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Scoring Data",
                    content = @Content(schema = @Schema(implementation = ScoringDataDto.class)),
                    required = true),
            operationId = "calculation",
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = "200",
                            content = @Content(schema =
                            @Schema(implementation = CreditDto.class))),
                    @ApiResponse(description = "Bad Request",
                            responseCode = "400"),
                    @ApiResponse(description = "Unprocessable Entity",
                            responseCode = "422")
            }
    )
    @PostMapping("/calculation")
    ResponseEntity<CreditDto> calculation(@RequestBody @Valid ScoringDataDto scoringDataDto);
}
