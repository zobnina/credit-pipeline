package ru.neoflex.trainingcenter.msdeal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.neoflex.trainingcenter.msdeal.model.dto.FinishRegistrationRequestDto;
import ru.neoflex.trainingcenter.msdeal.model.dto.LoanApplicationRequestDto;
import ru.neoflex.trainingcenter.msdeal.model.dto.LoanOfferDto;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Tag(name = "Deal Controller",
        description = "Methods of Deal Controller")
@Validated
@RequestMapping(value = "/deal",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public interface DealController {

    @Operation(
            method = "POST",
            summary = "Calculation of possible loan terms",
            description = "A POST request is sent to /conveyor/offers MS conveyor",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Loan Application Request",
                    content = @Content(schema = @Schema(implementation = LoanApplicationRequestDto.class)),
                    required = true),
            operationId = "application",
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = "200",
                            content = @Content(array =
                            @ArraySchema(schema = @Schema(implementation = LoanOfferDto.class)))),
                    @ApiResponse(description = "Bad Request",
                            responseCode = "400")
            }
    )
    @PostMapping("/application")
    ResponseEntity<List<LoanOfferDto>> application(
            @RequestBody @Valid LoanApplicationRequestDto loanApplicationRequestDto);

    @Operation(
            method = "PUT",
            summary = "Choosing one of the offers",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Loan Offer",
                    content = @Content(schema = @Schema(implementation = LoanOfferDto.class)),
                    required = true),
            operationId = "offer",
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = "200"),
                    @ApiResponse(description = "Bad Request",
                            responseCode = "400"),
                    @ApiResponse(description = "Not Found",
                            responseCode = "404")
            }
    )
    @PutMapping("/offer")
    ResponseEntity<Void> offer(@RequestBody @Valid LoanOfferDto loanOfferDto);

    @Operation(
            method = "PUT",
            summary = "Full credit calculations",
            parameters = @Parameter(
                    name = "applicationId",
                    in = ParameterIn.PATH,
                    required = true,
                    description = "Application Id",
                    schema = @Schema(implementation = UUID.class, example = "34acf27c-e09d-47f8-812e-fe41160e0605")
            ),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Loan Offer",
                    content = @Content(schema = @Schema(implementation = FinishRegistrationRequestDto.class)),
                    required = true),
            operationId = "calculate",
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = "200"),
                    @ApiResponse(description = "Bad Request",
                            responseCode = "400"),
                    @ApiResponse(description = "Not Found",
                            responseCode = "404")
            }
    )
    @PutMapping("/calculate/{applicationId}")
    ResponseEntity<Void> calculate(
            @PathVariable UUID applicationId,
            @RequestBody @Valid FinishRegistrationRequestDto finishRegistrationRequestDto)
            ;
}
