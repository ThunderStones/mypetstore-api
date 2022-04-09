package org.csu.mypetstore.api.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.csu.mypetstore.api.entity.Address;
import org.csu.mypetstore.api.entity.LineItem;
import org.csu.mypetstore.api.entity.Order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Data
public class OrderDTO {
    private int orderId;
    private String userId;
    private Date orderDate;
    @JsonIgnore
    private String courier;
    private BigDecimal totalPrice;
    private String name;
    private String addressDetail;
    private String phone;
    private String addressDataId;
    private int addressId;
    private List<LineItemDTO> lineItems = new ArrayList<>();


    public void initOrder(String userId, CartDTO cart, Address address) {
        this.userId = userId;
        orderDate = new Date();
        this.name = address.getName();
        addressDetail = address.getAddressDetail();
        phone = address.getPhone();
        addressDataId = address.getAddressId();
        addressId = address.getId();
        totalPrice = cart.getSubTotal();
        for (CartItemDTO cartItemDTO : cart.getCartItemList()) {
            LineItemDTO lineItemDTO = new LineItemDTO(lineItems.size() + 1, cartItemDTO);
            lineItems.add(lineItemDTO);
        }
    }

    public void addLineItem(CartItemDTO cartItem) {
        LineItemDTO lineItem = new LineItemDTO(lineItems.size() + 1, cartItem);
        addLineItem(lineItem);
    }

    public void addLineItem(LineItemDTO lineItem) {
        lineItems.add(lineItem);
    }

    public void calculateTotalPrice() {
        totalPrice = lineItems.stream()
                .map(lineItemDTO -> {
                    lineItemDTO.calculateTotal();
                    return lineItemDTO.getTotal();
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
