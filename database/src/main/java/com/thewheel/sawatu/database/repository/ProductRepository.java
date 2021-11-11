package com.thewheel.sawatu.database.repository;

import com.thewheel.sawatu.database.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}