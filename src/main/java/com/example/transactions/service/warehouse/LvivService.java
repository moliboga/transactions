package com.example.transactions.service.warehouse;

import com.example.transactions.model.product.LvivProduct;
import com.example.transactions.repository.warehouse.WarehouseRepo;
import org.springframework.stereotype.Service;

@Service
public class LvivService extends WarehouseService<LvivProduct>{

    public LvivService(WarehouseRepo<LvivProduct> warehouseRepo) {
        super(warehouseRepo);
    }
}
