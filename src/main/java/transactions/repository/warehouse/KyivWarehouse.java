package transactions.repository.warehouse;

import transactions.model.product.KyivProduct;
import org.springframework.stereotype.Repository;

@Repository
public interface KyivWarehouse extends WarehouseRepo<KyivProduct> {
}
