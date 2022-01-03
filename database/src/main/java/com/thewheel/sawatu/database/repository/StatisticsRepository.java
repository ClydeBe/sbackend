package com.thewheel.sawatu.database.repository;

import com.thewheel.sawatu.database.model.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StatisticsRepository extends JpaRepository<Statistic, Long> {
    public Optional<Statistic> findFirstByUsernameOrderByIdDesc(@Param("username") String username);
}