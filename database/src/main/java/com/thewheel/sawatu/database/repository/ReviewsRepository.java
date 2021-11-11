package com.thewheel.sawatu.database.repository;

import com.thewheel.sawatu.database.model.Reviews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ReviewsRepository extends JpaRepository<Reviews, Long> {
    public Page<Reviews> findAllBySelfNameOrderByIdDesc(@Param("username") String username, Pageable pageable);
}