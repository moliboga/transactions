package com.example.transactions.service.warehouse;

import com.example.transactions.model.product.LvivProduct;
import com.example.transactions.repository.warehouse.LvivWarehouse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LvivService {
    private final LvivWarehouse warehouse;

    public LvivService(LvivWarehouse warehouse) {
        this.warehouse = warehouse;
    }

    public List<LvivProduct> getAll() {
        return warehouse.findAll();
    }

    public LvivProduct getByProductName(String productName) {
        if (warehouse.existsByProductName(productName))
            return warehouse.findByProductName(productName).get();
        return null;
    }

    public void transfer(LvivProduct details){
        LvivProduct product = getByProductName(details.getProductName());
        if (product != null){
            int newAmount = product.getAmount() + details.getAmount();
            if (newAmount < 0){
                throw new IllegalArgumentException("There are not so many products in the warehouse");
            }
            product.setAmount(newAmount);
        }
        else {
            product = details;
        }
        warehouse.save(product);
    }
}
