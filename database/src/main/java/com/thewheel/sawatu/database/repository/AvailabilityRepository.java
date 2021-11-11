package com.thewheel.sawatu.database.repository;

import com.thewheel.sawatu.database.model.Availability;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    public Page<Availability> findAllByUserNameOrderByIdDesc(@Param("username") String username, Pageable pageabe);
}