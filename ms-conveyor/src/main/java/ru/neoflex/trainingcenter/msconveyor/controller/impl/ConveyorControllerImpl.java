package ru.neoflex.trainingcenter.msconveyor.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.neoflex.trainingcenter.msconveyor.controller.ConveyorController;
import ru.neoflex.trainingcenter.msconveyor.dto.CreditDto;
import ru.neoflex.trainingcenter.msconveyor.dto.LoanApplicationRequestDto;
import ru.neoflex.trainingcenter.msconveyor.dto.LoanOfferDto;
import ru.neoflex.trainingcenter.msconveyor.dto.ScoringDataDto;
import ru.neoflex.trainingcenter.msconveyor.service.ConveyorService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ConveyorControllerImpl implements ConveyorController {

    private final ConveyorService conveyorService;

    @Override
    public ResponseEntity<List<LoanOfferDto>> offers(LoanApplicationRequestDto loanApplicationRequestDto) {

        return ResponseEntity.ok(conveyorService.offers(loanApplicationRequestDto));
    }

    @Override
    public ResponseEntity<CreditDto> calculation(ScoringDataDto scoringDataDto) {

        return ResponseEntity.ok(conveyorService.calculation(scoringDataDto));
    }
}
