package ru.neoflex.trainingcenter.msdeal.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.neoflex.trainingcenter.msdeal.model.entity.Application;

import java.util.UUID;

public interface ApplicationRepository extends JpaRepository<Application, UUID> {
}
