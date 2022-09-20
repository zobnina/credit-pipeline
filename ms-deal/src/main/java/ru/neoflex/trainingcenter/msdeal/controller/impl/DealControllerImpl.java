package ru.neoflex.trainingcenter.msdeal.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.azobnina.liblog.InfoLog;
import ru.neoflex.trainingcenter.msdeal.controller.DealController;
import ru.neoflex.trainingcenter.msdeal.model.dto.FinishRegistrationRequestDto;
import ru.neoflex.trainingcenter.msdeal.model.dto.LoanApplicationRequestDto;
import ru.neoflex.trainingcenter.msdeal.model.dto.LoanOfferDto;
import ru.neoflex.trainingcenter.msdeal.service.DealService;

import java.util.List;
import java.util.UUID;

@InfoLog
@RestController
@RequiredArgsConstructor
public class DealControllerImpl implements DealController {

    private final DealService dealService;

    @Override
    public ResponseEntity<List<LoanOfferDto>> application(LoanApplicationRequestDto loanApplicationRequestDto) {

        return ResponseEntity.ok(dealService.application(loanApplicationRequestDto));
    }

    @Override
    public ResponseEntity<Void> offer(LoanOfferDto loanOfferDto) {

        dealService.offer(loanOfferDto);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> calculate(UUID applicationId, FinishRegistrationRequestDto finishRegistrationRequestDto) {

        dealService.calculate(applicationId, finishRegistrationRequestDto);

        return ResponseEntity.ok().build();
    }
}
