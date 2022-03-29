package org.csu.mypetstore.api.vo;

import lombok.Data;
import org.csu.mypetstore.api.entity.Item;

import java.math.BigDecimal;
@Data
public class CartItemVO {
    private String itemId;
    private BigDecimal unitPrice;
    private int quantity;
    private boolean inStock;
    private BigDecimal total;

    public void calculateTotal() {
        total = unitPrice.multiply(new BigDecimal(quantity));
    }
}
