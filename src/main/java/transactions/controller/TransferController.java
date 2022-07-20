package transactions.controller;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import transactions.dto.NewProduct;
import transactions.dto.TransferInfo;
import transactions.model.Log;
import transactions.model.product.KyivProduct;
import transactions.model.product.LvivProduct;
import transactions.service.LogService;
import transactions.service.TransferService;
import transactions.service.warehouse.KyivService;
import transactions.service.warehouse.LvivService;

import java.util.List;

@RestController
@RequestMapping("/api/transfer")
public class TransferController {

    private final KyivService kyivService;
    private final LvivService lvivService;
    private final LogService logService;
    private final TransferService transferService;

    public TransferController(KyivService kyivService, LvivService lvivService, LogService logService, TransferService transferService) {
        this.kyivService = kyivService;
        this.lvivService = lvivService;
        this.logService = logService;
        this.transferService = transferService;
    }

    @GetMapping("/kyiv")
    public List<KyivProduct> getKyivWarehouse() {
        return kyivService.getAll();
    }

    @GetMapping("/lviv")
    public List<LvivProduct> getLvivWarehouse() {
        return lvivService.getAll();
    }

    @Transactional
    @PutMapping
    public TransferInfo transferFromLvivToKyiv(@RequestBody TransferInfo transferInfo) {
        String fromStr = transferInfo.getFrom();
        String toStr = transferInfo.getTo();
        for (NewProduct newProduct : transferInfo.getProducts()) {
            transferService.transfer(newProduct, fromStr, toStr);
        }
        return transferInfo;
    }

    @GetMapping("/logs")
    public List<Log> getLogs() {
        return logService.getAll();
    }
}
