package com.example.transactions.model;

import com.example.transactions.model.product.KyivProduct;
import lombok.Data;

import java.util.List;

@Data
public class TransferInfo {
    private String from;
    private String to;
    private List<KyivProduct> products;
}
