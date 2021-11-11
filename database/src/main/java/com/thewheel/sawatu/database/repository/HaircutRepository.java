package com.thewheel.sawatu.database.repository;

import com.thewheel.sawatu.database.model.Haircut;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HaircutRepository extends JpaRepository<Haircut, Long> {
}