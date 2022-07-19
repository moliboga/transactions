package transactions.service.warehouse;

import transactions.model.product.LvivProduct;
import transactions.repository.warehouse.WarehouseRepo;
import org.springframework.stereotype.Service;

@Service
public class LvivService extends WarehouseService<LvivProduct>{

    public LvivService(WarehouseRepo<LvivProduct> warehouseRepo) {
        super(warehouseRepo);
    }
}
