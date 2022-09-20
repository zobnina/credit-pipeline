package ru.neoflex.trainingcenter.msdeal.service;

import ru.neoflex.trainingcenter.msdeal.model.dto.FinishRegistrationRequestDto;
import ru.neoflex.trainingcenter.msdeal.model.dto.LoanApplicationRequestDto;
import ru.neoflex.trainingcenter.msdeal.model.dto.LoanOfferDto;

import java.util.List;
import java.util.UUID;

public interface DealService {

    List<LoanOfferDto> application(LoanApplicationRequestDto loanApplicationRequestDto);

    void offer(LoanOfferDto loanOfferDto);

    void calculate(UUID applicationId, FinishRegistrationRequestDto finishRegistrationRequestDto);
}
