package com.thewheel.sawatu.database.repository;

import com.thewheel.sawatu.database.model.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    public Optional<Availability> findFirstByUserName(@Param("username") String username);
}