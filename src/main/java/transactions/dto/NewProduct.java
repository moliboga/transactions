package transactions.dto;

import lombok.Data;

@Data
public class NewProduct {
    private String productName;
    private int amount;
}
