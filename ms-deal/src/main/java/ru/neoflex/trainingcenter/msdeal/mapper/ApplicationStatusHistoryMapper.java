package ru.neoflex.trainingcenter.msdeal.mapper;

import org.mapstruct.Mapper;
import ru.neoflex.trainingcenter.msdeal.model.entity.Application;
import ru.neoflex.trainingcenter.msdeal.model.entity.ApplicationStatusHistory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface ApplicationStatusHistoryMapper {

    ApplicationStatusHistory applicationToApplicationHistory(Application application);

    default ApplicationStatusHistory createApplication(Application application) {

        ApplicationStatusHistory applicationStatusHistory = applicationToApplicationHistory(application);
        applicationStatusHistory.setTime(LocalDateTime.now());

        return applicationStatusHistory;
    }

    default List<ApplicationStatusHistory> applicationStatusHistories(Application application) {

        List<ApplicationStatusHistory> result = Optional.ofNullable(application.getStatusHistory())
                .orElse(new ArrayList<>());
        result.add(createApplication(application));

        return result;
    }
}
