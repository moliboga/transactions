package com.example.transactions.controller;

import com.example.transactions.dto.NewProduct;
import com.example.transactions.model.Log;
import com.example.transactions.dto.TransferInfo;
import com.example.transactions.model.product.KyivProduct;
import com.example.transactions.model.product.LvivProduct;
import com.example.transactions.service.LogService;
import com.example.transactions.service.warehouse.KyivService;
import com.example.transactions.service.warehouse.LvivService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transfer")
public class TransferController {
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

    @PutMapping("/kyiv")
    public List<KyivProduct> transferToKyiv(@RequestBody List<KyivProduct> instances) {
        instances.forEach(kyivService::transfer);
        return kyivService.getAll();
    }

    @PutMapping("/lviv")
    public List<LvivProduct> transferToLviv(@RequestBody List<LvivProduct> instances) {
        instances.forEach(lvivService::transfer);
        return lvivService.getAll();
    }

    @Transactional
    void transfer(KyivProduct kyivProduct, String fromStr, String toStr) {
        kyivService.transfer(kyivProduct);

        LvivProduct lvivProduct = new LvivProduct();
        lvivProduct.setProductName(kyivProduct.getProductName());
        lvivProduct.setAmount(-1 * kyivProduct.getAmount());

        if (lvivService.getByProductName(lvivProduct.getProductName()) == null) {
            throw new IllegalArgumentException("No such product in the Lviv warehouse");
        }
        lvivService.transfer(lvivProduct);

        Log log = new Log();
        log.setAmount(kyivProduct.getAmount());

        log.setFromWarehouse(fromStr);
        log.setToWarehouse(toStr);
        log.setProductName(kyivProduct.getProductName());
        logService.AddLog(log);
    }

    @PutMapping()
    public TransferInfo transferFromLvivToKyiv(@RequestBody TransferInfo transferInfo) {
        String from = transferInfo.getFrom();
        String to = transferInfo.getTo();
        transferInfo.getProducts().forEach(product ->
                transfer(product, "", ""));
        return transferInfo;
    }

    @PostMapping("/kyiv")
    public ResponseEntity<KyivProduct> addNewToKyiv(@RequestBody NewProduct newProduct) {
        return ResponseEntity.ok(kyivService.add(KyivProduct.builder()
                .productName(newProduct.getProductName())
                .amount(newProduct.getAmount())
                .build()));
    }

    @PostMapping("/lviv")
    public ResponseEntity<LvivProduct> addNewToLviv(@RequestBody NewProduct newProduct) {
        return ResponseEntity.ok(lvivService.add(LvivProduct.builder()
                .productName(newProduct.getProductName())
                .amount(newProduct.getAmount())
                .build()));
    }

    @GetMapping("/logs")
    public List<Log> getLogs() {
        return logService.GetAll();
    }
}
