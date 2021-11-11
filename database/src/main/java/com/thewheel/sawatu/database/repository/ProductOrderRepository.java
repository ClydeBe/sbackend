package com.thewheel.sawatu.database.repository;

import com.thewheel.sawatu.database.model.ProductOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {
    public Page<ProductOrder> findAllByUserNameOrderByIdDesc(@Param("username") String username, Pageable pageable);
}