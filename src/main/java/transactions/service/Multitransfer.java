package transactions.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import transactions.dto.NewProduct;

import java.util.List;

@Service
public class Multitransfer {
    @Autowired
    private TransferService transferService;

    @Transactional
    public void multiTransfer(List<NewProduct> products, String fromStr, String toStr){
        for (NewProduct newProduct : products) {
            transferService.transfer(newProduct, fromStr, toStr);
        }
//        transferService.transfer(products.get(0), fromStr, toStr);
//        transferService.transfer(products.get(1), fromStr, toStr);
    }
}
