package transactions.repository.warehouse;

import transactions.model.product.LvivProduct;
import org.springframework.stereotype.Repository;

@Repository
public interface LvivWarehouse extends WarehouseRepo<LvivProduct> {
}
