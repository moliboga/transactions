package com.example.transactions.service.warehouse;

import com.example.transactions.model.product.KyivProduct;
import com.example.transactions.repository.warehouse.KyivWarehouse;
import com.example.transactions.repository.warehouse.WarehouseRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KyivService extends WarehouseService<KyivProduct> {

    public KyivService(WarehouseRepo<KyivProduct> warehouseRepo) {
        super(warehouseRepo);
    }
}
