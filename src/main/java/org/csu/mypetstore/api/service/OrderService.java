package org.csu.mypetstore.api.service;

import org.csu.mypetstore.api.common.CommonResponse;
import org.csu.mypetstore.api.dto.CartDTO;
import org.csu.mypetstore.api.dto.OrderDTO;
import org.csu.mypetstore.api.entity.Order;

import java.util.List;

public interface OrderService {

    OrderDTO getOrder(String orderId);

    void insertOrder(OrderDTO orderDTO);

    List<Order> getOrderList(String username);

    Order placeOrder(String username, int addressId, int[] cartItemIds);
}
