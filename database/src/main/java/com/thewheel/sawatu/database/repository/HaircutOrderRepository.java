package com.thewheel.sawatu.database.repository;

import com.thewheel.sawatu.database.model.HaircutOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface HaircutOrderRepository extends JpaRepository<HaircutOrder, Long> {
    public Page<HaircutOrder> findAllByClientNameOrderByIdDesc(@Param("username") String username, Pageable pageable);
}