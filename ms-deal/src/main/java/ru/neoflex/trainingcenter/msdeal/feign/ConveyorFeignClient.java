package ru.neoflex.trainingcenter.msdeal.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.neoflex.trainingcenter.msdeal.config.FeignClientConfiguration;
import ru.neoflex.trainingcenter.msdeal.model.dto.CreditDto;
import ru.neoflex.trainingcenter.msdeal.model.dto.LoanApplicationRequestDto;
import ru.neoflex.trainingcenter.msdeal.model.dto.LoanOfferDto;
import ru.neoflex.trainingcenter.msdeal.model.dto.ScoringDataDto;

import java.util.List;

@FeignClient(value = "conveyorClient",
        url = "${application.url.conveyor}",
        configuration = FeignClientConfiguration.class)
public interface ConveyorFeignClient {

    @PostMapping("/conveyor/offers")
    List<LoanOfferDto> postOffers(@RequestBody LoanApplicationRequestDto loanApplicationRequestDto);

    @PostMapping("/conveyor/calculation")
    CreditDto postCalculation(@RequestBody ScoringDataDto scoringDataDto);
}
