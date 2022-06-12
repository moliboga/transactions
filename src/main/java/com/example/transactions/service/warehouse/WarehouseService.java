package com.example.transactions.service.warehouse;

import com.example.transactions.model.Product;
import com.example.transactions.repository.warehouse.WarehouseRepo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class WarehouseService<U extends Product> {

    private final WarehouseRepo<U> warehouseRepo;

    public WarehouseService(WarehouseRepo<U> warehouseRepo) {
        this.warehouseRepo = warehouseRepo;
    }

    public List<U> getAll() {
        return warehouseRepo.findAll();
    }

    public U getByProductName(String productName) {
        if (warehouseRepo.existsByProductName(productName))
            return warehouseRepo.findByProductName(productName).get();
        return null;
    }

    @Transactional
    public void transfer(U details){
        U product = getByProductName(details.getProductName());
        if (product != null) {
            int newAmount = product.getAmount() + details.getAmount();
            if (newAmount < 0) {
                throw new IllegalArgumentException("There are not so many "
                        + product.getProductName() + " in the warehouse");
            }
            product.setAmount(newAmount);
        } else {
            if (details.getAmount() < 0){
                throw new IllegalArgumentException("There are not so many "
                        + details.getProductName() + " in the warehouse");
            }
            product = details;
        }
        warehouseRepo.save(product);
    }
}
