package ru.neoflex.trainingcenter.msdeal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Tag(name = "Document Controller",
        description = "Methods of Document Controller")
@Validated
@RequestMapping(value = "/deal/document",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public interface DocumentController {

    @Operation(
            method = "POST",
            summary = "Send documents request",
            parameters = @Parameter(
                    name = "applicationId",
                    in = ParameterIn.PATH,
                    required = true,
                    description = "Application Id",
                    schema = @Schema(implementation = UUID.class, example = "34acf27c-e09d-47f8-812e-fe41160e0605")
            ),
            operationId = "send",
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = "200"),
                    @ApiResponse(description = "Not Found",
                            responseCode = "404")
            }
    )
    @PostMapping("/{applicationId}/send")
    ResponseEntity<Void> send(@PathVariable UUID applicationId) ;

    @Operation(
            method = "POST",
            summary = "Sign documents request",
            parameters = @Parameter(
                    name = "applicationId",
                    in = ParameterIn.PATH,
                    required = true,
                    description = "Application Id",
                    schema = @Schema(implementation = UUID.class, example = "34acf27c-e09d-47f8-812e-fe41160e0605")
            ),
            operationId = "sign",
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = "200"),
                    @ApiResponse(description = "Not Found",
                            responseCode = "404")
            }
    )
    @PostMapping("/{applicationId}/sign")
    ResponseEntity<Void> sign(@PathVariable UUID applicationId) ;

    @Operation(
            method = "POST",
            summary = "Sign documents",
            parameters = @Parameter(
                    name = "applicationId",
                    in = ParameterIn.PATH,
                    required = true,
                    description = "Application Id",
                    schema = @Schema(implementation = UUID.class, example = "34acf27c-e09d-47f8-812e-fe41160e0605")
            ),
            operationId = "code",
            responses = {
                    @ApiResponse(description = "OK",
                            responseCode = "200"),
                    @ApiResponse(description = "Not Found",
                            responseCode = "404")
            }
    )
    @PostMapping("/{applicationId}/code")
    ResponseEntity<Void> code(@PathVariable UUID applicationId) ;
}
