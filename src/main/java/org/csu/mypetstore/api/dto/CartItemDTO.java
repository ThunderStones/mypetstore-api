package org.csu.mypetstore.api.dto;

import lombok.Data;
import org.csu.mypetstore.api.entity.Item;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Optional;

@Data
public class CartItemDTO {
    private int id;
    private Item item;
    private int quantity;
    private boolean inStock;
    private BigDecimal total;

    public void calculateTotal() {
        total = Optional.ofNullable(item).map(Item::getListPrice).map(v -> v.multiply(new BigDecimal(quantity))).orElse(null);
    }
    public void incrementQuantity() {
        quantity++;
        calculateTotal();
    }
}
