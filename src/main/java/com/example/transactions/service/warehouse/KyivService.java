package com.example.transactions.service.warehouse;

import com.example.transactions.model.product.KyivProduct;
import com.example.transactions.repository.warehouse.KyivWarehouse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KyivService {
    private final KyivWarehouse warehouse;

    public KyivService(KyivWarehouse warehouse) {
        this.warehouse = warehouse;
    }

    public List<KyivProduct> getAll() {
        return warehouse.findAll();
    }

    public KyivProduct getByProductName(String productName) {
        if (warehouse.existsByProductName(productName))
            return warehouse.findByProductName(productName).get();
        return null;
    }

    public void transfer(KyivProduct details){
        KyivProduct product = getByProductName(details.getProductName());
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
