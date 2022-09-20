package ru.neoflex.trainingcenter.msdeal.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.azobnina.liblog.DebugLog;
import ru.neoflex.trainingcenter.msdeal.exception.EntityNotFoundException;
import ru.neoflex.trainingcenter.msdeal.feign.ConveyorFeignClient;
import ru.neoflex.trainingcenter.msdeal.mapper.ApplicationMapper;
import ru.neoflex.trainingcenter.msdeal.mapper.ApplicationStatusHistoryMapper;
import ru.neoflex.trainingcenter.msdeal.mapper.ClientMapper;
import ru.neoflex.trainingcenter.msdeal.mapper.CreditMapper;
import ru.neoflex.trainingcenter.msdeal.mapper.PassportMapper;
import ru.neoflex.trainingcenter.msdeal.mapper.ScoringDataMapper;
import ru.neoflex.trainingcenter.msdeal.model.ApplicationStatus;
import ru.neoflex.trainingcenter.msdeal.model.dto.CreditDto;
import ru.neoflex.trainingcenter.msdeal.model.dto.FinishRegistrationRequestDto;
import ru.neoflex.trainingcenter.msdeal.model.dto.LoanApplicationRequestDto;
import ru.neoflex.trainingcenter.msdeal.model.dto.LoanOfferDto;
import ru.neoflex.trainingcenter.msdeal.model.dto.ScoringDataDto;
import ru.neoflex.trainingcenter.msdeal.model.entity.Application;
import ru.neoflex.trainingcenter.msdeal.model.entity.ApplicationStatusHistory;
import ru.neoflex.trainingcenter.msdeal.model.entity.Client;
import ru.neoflex.trainingcenter.msdeal.model.entity.Credit;
import ru.neoflex.trainingcenter.msdeal.model.entity.Passport;
import ru.neoflex.trainingcenter.msdeal.repo.ApplicationRepository;
import ru.neoflex.trainingcenter.msdeal.service.DealService;
import ru.neoflex.trainingcenter.msdeal.service.EmailMessageProducer;

import java.util.List;
import java.util.UUID;

import static ru.neoflex.trainingcenter.msdeal.config.Constant.APPLICATION;

@DebugLog
@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {

    private final ApplicationRepository applicationRepository;
    private final ConveyorFeignClient conveyorFeignClient;
    private final EmailMessageProducer emailMessageProducer;
    private final ApplicationMapper applicationMapper;
    private final ApplicationStatusHistoryMapper applicationStatusHistoryMapper;
    private final ClientMapper clientMapper;
    private final CreditMapper creditMapper;
    private final PassportMapper passportMapper;
    private final ScoringDataMapper scoringDataMapper;

    @Override
    public List<LoanOfferDto> application(LoanApplicationRequestDto loanApplicationRequestDto) {

        final Passport passport = passportMapper.loanApplicationRequestDtoToPassport(loanApplicationRequestDto);
        final Client client = clientMapper.createClient(loanApplicationRequestDto, passport);
        final Application application = applicationRepository.save(applicationMapper.createApplication(client));
        final List<LoanOfferDto> loanOfferDtos = conveyorFeignClient.postOffers(loanApplicationRequestDto);
        loanOfferDtos.forEach(loanOfferDto -> loanOfferDto.setApplicationId(application.getId()));

        return loanOfferDtos;
    }

    @Override
    public void offer(LoanOfferDto loanOfferDto) {

        final Application application = applicationRepository.findById(loanOfferDto.getApplicationId())
                .orElseThrow(() -> new EntityNotFoundException(APPLICATION));
        application.setStatus(ApplicationStatus.APPROVED);
        application.setAppliedOffer(loanOfferDto);
        final List<ApplicationStatusHistory> histories =
                applicationStatusHistoryMapper.applicationStatusHistories(application);
        application.setStatusHistory(histories);
        applicationRepository.save(application);
        emailMessageProducer.finishRegistration(application.getId(), application.getClient().getEmail());
    }

    @Transactional
    @Override
    public void calculate(UUID applicationId, FinishRegistrationRequestDto finishRegistrationRequestDto) {

        final Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new EntityNotFoundException(APPLICATION));
        final ScoringDataDto scoringDataDto = scoringDataMapper.createScoringDataDto(
                application, finishRegistrationRequestDto);
        CreditDto creditDto = conveyorFeignClient.postCalculation(scoringDataDto);
        final Credit credit = creditMapper.creditDtoToCredit(creditDto);
        application.setStatusHistory(applicationStatusHistoryMapper.applicationStatusHistories(application));
        application.setStatus(ApplicationStatus.CC_APPROVED);
        application.setCredit(credit);
        applicationRepository.save(application);
        emailMessageProducer.createDocuments(application.getId(), application.getClient().getEmail());
    }
}
