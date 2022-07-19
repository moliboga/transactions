package transactions.service.warehouse;

import transactions.model.product.KyivProduct;
import transactions.repository.warehouse.WarehouseRepo;
import org.springframework.stereotype.Service;

@Service
public class KyivService extends WarehouseService<KyivProduct> {

    public KyivService(WarehouseRepo<KyivProduct> warehouseRepo) {
        super(warehouseRepo);
    }
}
