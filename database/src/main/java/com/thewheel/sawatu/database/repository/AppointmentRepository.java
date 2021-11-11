package com.thewheel.sawatu.database.repository;

import com.thewheel.sawatu.database.model.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    public Page<Appointment> findAllByClientNameOrderByIdDesc(@Param("username") String  username, Pageable pageabe);
    public Page<Appointment> findAllByVendorNameOrderByIdDesc(@Param("username") String  username, Pageable pageabe);
}