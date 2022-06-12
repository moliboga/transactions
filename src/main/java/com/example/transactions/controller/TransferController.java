package com.example.transactions.controller;

import com.example.transactions.dto.NewProduct;
import com.example.transactions.model.Log;
import com.example.transactions.dto.TransferInfo;
import com.example.transactions.model.product.KyivProduct;
import com.example.transactions.model.product.LvivProduct;
import com.example.transactions.service.LogService;
import com.example.transactions.service.warehouse.KyivService;
import com.example.transactions.service.warehouse.LvivService;
import com.example.transactions.service.warehouse.WarehouseService;
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
    public List<KyivProduct> transferToKyiv(@RequestBody List<NewProduct> instances) {
        instances.forEach(newProduct -> kyivService.transfer(KyivProduct.builder()
                .productName(newProduct.getProductName())
                .amount(newProduct.getAmount())
                .build()));
        return kyivService.getAll();
    }

    @PutMapping("/lviv")
    public List<LvivProduct> transferToLviv(@RequestBody List<NewProduct> instances) {
        instances.forEach(newProduct -> lvivService.transfer(LvivProduct.builder()
                .productName(newProduct.getProductName())
                .amount(newProduct.getAmount())
                .build()));
        return lvivService.getAll();
    }

    @Transactional
    void transfer(NewProduct newProduct, String fromStr, String toStr) {

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
                    throw new IllegalArgumentException("Wrong TO warehouse format");
        }

        if (fromStr == null) {

        } else if ("lviv".equals(fromStr)) {

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
            throw new IllegalStateException("Unexpected value: " + fromStr);
        }

        logService.AddLog(Log
                .builder()
                .productName(newProduct.getProductName())
                .amount(newProduct.getAmount())
                .fromWarehouse(fromStr)
                .toWarehouse(toStr)
                .build());
    }

    @PutMapping()
    public TransferInfo transferFromLvivToKyiv(@RequestBody TransferInfo transferInfo) {
        String fromStr = transferInfo.getFrom();
        String toStr = transferInfo.getTo();
        transferInfo.getProducts().forEach(product ->
                transfer(product, fromStr, toStr));
        return transferInfo;
    }

    @GetMapping("/logs")
    public List<Log> getLogs() {
        return logService.GetAll();
    }
}
