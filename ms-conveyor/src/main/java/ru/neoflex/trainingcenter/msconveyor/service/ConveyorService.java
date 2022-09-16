package ru.neoflex.trainingcenter.msconveyor.service;

import ru.neoflex.trainingcenter.msconveyor.dto.CreditDto;
import ru.neoflex.trainingcenter.msconveyor.dto.LoanApplicationRequestDto;
import ru.neoflex.trainingcenter.msconveyor.dto.LoanOfferDto;
import ru.neoflex.trainingcenter.msconveyor.dto.ScoringDataDto;

import java.util.List;

public interface ConveyorService {

    List<LoanOfferDto> offers(LoanApplicationRequestDto loanApplicationRequestDto);

    CreditDto calculation(ScoringDataDto scoringDataDto);
}
