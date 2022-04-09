package org.csu.mypetstore.api.dto;

import lombok.Data;
import org.csu.mypetstore.api.entity.Item;

import java.math.BigDecimal;
import java.util.Optional;

@Data
public class LineItemDTO {
    private int orderId;
    private int lineNumber;
    private int quantity;
    private String itemId;
    private BigDecimal unitPrice;
    private Item item;
    private BigDecimal total;
    public LineItemDTO() {
    }

    public LineItemDTO(int lineNumber, CartItemDTO cartItem) {
        this.lineNumber = lineNumber;
        this.quantity = cartItem.getQuantity();
        this.itemId = cartItem.getItem().getItemId();
        this.unitPrice = cartItem.getItem().getListPrice();
        this.item = cartItem.getItem();
        calculateTotal();
    }
    public void calculateTotal() {
        total = Optional.ofNullable(item).map(Item::getListPrice).map(v -> v.multiply(new BigDecimal(quantity)))
                .orElse(null);
    }
}
