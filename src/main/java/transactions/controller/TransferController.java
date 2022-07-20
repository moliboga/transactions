package transactions.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import transactions.dto.TransferInfo;
import transactions.model.Log;
import transactions.model.product.KyivProduct;
import transactions.model.product.LvivProduct;
import transactions.service.LogService;
import transactions.service.Multitransfer;
import transactions.service.TransferService;
import transactions.service.warehouse.KyivService;
import transactions.service.warehouse.LvivService;

import java.util.List;

@RestController
@RequestMapping("/api/transfer")
public class TransferController {

    @Autowired
    private Multitransfer multiTransfer;

    private final KyivService kyivService;
    private final LvivService lvivService;
    private final LogService logService;

    public TransferController(KyivService kyivService, LvivService lvivService, LogService logService) {
        this.kyivService = kyivService;
        this.lvivService = lvivService;
        this.logService = logService;
    }

    @GetMapping("/kyiv")
    public List<KyivProduct> getKyivWarehouse() {
        return kyivService.getAll();
    }

    @GetMapping("/lviv")
    public List<LvivProduct> getLvivWarehouse() {
        return lvivService.getAll();
    }

    @PutMapping
    public TransferInfo transferFromLvivToKyiv(@RequestBody TransferInfo transferInfo) {
        String fromStr = transferInfo.getFrom();
        String toStr = transferInfo.getTo();
        multiTransfer.multiTransfer(transferInfo.getProducts(), fromStr, toStr);
        return transferInfo;
    }

    @GetMapping("/logs")
    public List<Log> getLogs() {
        return logService.getAll();
    }
}
