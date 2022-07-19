package transactions.dto;

import lombok.Data;

import java.util.List;

@Data
public class TransferInfo {
    private String from;
    private String to;
    private List<NewProduct> products;
}
