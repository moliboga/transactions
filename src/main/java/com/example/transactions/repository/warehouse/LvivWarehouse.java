package com.example.transactions.repository.warehouse;

import com.example.transactions.model.product.LvivProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LvivWarehouse extends WarehouseRepo<LvivProduct> {
}
