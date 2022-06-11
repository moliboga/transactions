package com.example.transactions.repository.warehouse;

import com.example.transactions.model.product.KyivProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KyivWarehouse extends WarehouseRepo<KyivProduct> {
}
