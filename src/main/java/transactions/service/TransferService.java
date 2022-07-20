package transactions.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import transactions.dto.NewProduct;
import transactions.model.Log;
import transactions.model.product.KyivProduct;
import transactions.model.product.LvivProduct;
import transactions.service.warehouse.KyivService;
import transactions.service.warehouse.LvivService;

import java.util.List;

@Service
public class TransferService {

    @Autowired
    private TransferService transferService;

    @Autowired
    private LvivService lvivService;

    @Autowired
    private KyivService kyivService;

    @Autowired
    private LogService logService;

    @Transactional
    public void transfer(NewProduct newProduct, String fromStr, String toStr){

        KyivProduct kyivProduct = KyivProduct.builder()
                .productName(newProduct.getProductName())
                .amount(newProduct.getAmount())
                .build();

        LvivProduct lvivProduct = LvivProduct.builder()
                .productName(newProduct.getProductName())
                .amount(newProduct.getAmount())
                .build();

        switch (toStr) {
            case "lviv" -> lvivService.transfer(lvivProduct);
            case "kyiv" -> kyivService.transfer(kyivProduct);
            case null, default ->
                    throw new IllegalArgumentException("Unexpected TO format:" + toStr);
        }

        if (fromStr != null)
            if ("lviv".equals(fromStr)) {

                if (lvivService.getByProductName(newProduct.getProductName()) == null) {
                    throw new IllegalArgumentException("No such product in the Lviv warehouse");
                }
                lvivProduct.setAmount(newProduct.getAmount() * -1);
                lvivService.transfer(lvivProduct);

            } else if ("kyiv".equals(fromStr)) {

                if (kyivService.getByProductName(newProduct.getProductName()) == null) {
                    throw new IllegalArgumentException("No such product in the Kyiv warehouse");
                }
                kyivProduct.setAmount(newProduct.getAmount() * -1);
                kyivService.transfer(kyivProduct);

            } else {
                throw new IllegalStateException("Unexpected FROM format: " + fromStr);
            }

        logService.add(Log.builder()
                .productName(newProduct.getProductName())
                .amount(newProduct.getAmount())
                .fromWarehouse(fromStr)
                .toWarehouse(toStr)
                .build());
    }
}
