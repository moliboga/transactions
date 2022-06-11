package com.example.transactions.dto;

import lombok.Data;
import org.springframework.lang.NonNull;

import javax.persistence.Column;

@Data
public class NewProduct {
    private String productName;
    private int amount;
}
