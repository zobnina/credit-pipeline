package ru.neoflex.trainingcenter.msdeal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.util.CollectionUtils;
import ru.neoflex.trainingcenter.msdeal.model.entity.Application;
import ru.neoflex.trainingcenter.msdeal.model.entity.ApplicationStatusHistory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", imports = LocalDateTime.class)
public interface ApplicationStatusHistoryMapper {

    ApplicationStatusHistoryMapper MAPPER =
            Mappers.getMapper(ApplicationStatusHistoryMapper.class);

    @Mapping(target = "changeType", ignore = true)
    @Mapping(target = "time", expression = "java(LocalDateTime.now())")
    ApplicationStatusHistory applicationToApplicationHistory(Application application);

    default List<ApplicationStatusHistory> applicationStatusHistories(Application application) {

        List<ApplicationStatusHistory> result = CollectionUtils.isEmpty(application.getStatusHistory()) ?
                new ArrayList<>() : application.getStatusHistory();
        result.add(applicationToApplicationHistory(application));

        return result;
    }
}
