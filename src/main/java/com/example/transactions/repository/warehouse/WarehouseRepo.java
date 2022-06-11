package com.example.transactions.repository.warehouse;

import com.example.transactions.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface WarehouseRepo<U extends Product> extends JpaRepository<U, Long> {
    Optional<U> findByProductName(String productName);
    Boolean existsByProductName(String productName);
}
