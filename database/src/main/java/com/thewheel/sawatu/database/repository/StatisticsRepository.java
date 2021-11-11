package com.thewheel.sawatu.database.repository;

import com.thewheel.sawatu.database.model.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
    public Optional<Statistics> findFirstByUserNameOrderByIdDesc(@Param("username") String username);
}