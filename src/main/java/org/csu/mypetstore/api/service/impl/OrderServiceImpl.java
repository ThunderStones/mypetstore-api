package org.csu.mypetstore.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.csu.mypetstore.api.dto.CartDTO;
import org.csu.mypetstore.api.dto.LineItemDTO;
import org.csu.mypetstore.api.dto.OrderDTO;
import org.csu.mypetstore.api.entity.*;
import org.csu.mypetstore.api.persistence.*;
import org.csu.mypetstore.api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Service("OrderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private LineItemMapper lineItemMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private SequenceMapper sequenceMapper;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private CartItemMapper cartItemMapper;


    @Override
    @Transactional
    public OrderDTO getOrder(String orderId) {
        Order order = orderMapper.selectById(orderId);
        QueryWrapper<LineItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("orderid", orderId);
        List<LineItem> lineItems = lineItemMapper.selectList(queryWrapper);
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setUserId(order.getUserId());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setCourier(order.getCourier());
        orderDTO.setTotalPrice(order.getTotalPrice());
        orderDTO.setName(order.getName());
        orderDTO.setAddressDetail(order.getAddressDetail());
        orderDTO.setPhone(order.getPhone());
        orderDTO.setAddressDataId(order.getAddressDataId());
        orderDTO.setAddressId(order.getAddressId());
        for (LineItem lineItem : lineItems) {
            LineItemDTO lineItemDTO = new LineItemDTO();
            lineItemDTO.setOrderId(lineItem.getOrderId());
            lineItemDTO.setLineNumber(lineItem.getLineNumber());
            lineItemDTO.setQuantity(lineItem.getQuantity());
            lineItemDTO.setItemId(lineItem.getItemId());
            lineItemDTO.setUnitPrice(lineItem.getUnitPrice());
            lineItemDTO.calculateTotal();
            lineItemDTO.setItem(itemMapper.selectById(lineItem.getItemId()));
            orderDTO.getLineItems().add(lineItemDTO);
        }
        orderDTO.calculateTotalPrice();
        return orderDTO;
    }

    @Override
    public void insertOrder(OrderDTO orderDTO) {

    }

    @Override
    public List<Order> getOrderList(String username) {
        return orderMapper.selectList(new QueryWrapper<Order>().eq("userid", username));
    }

    @Override
    @Transactional
    public Order placeOrder(String username, int addressId, int[] cartItemIds) {
        Order order = new Order();
        order.setOrderId(getNextId("ordernum"));
        order.setUserId(username);
        order.setOrderDate(new Date());
        order.setCourier("");
        order.setAddressId(addressId);
        Address address = addressMapper.selectById(addressId);
        order.setName(address.getName());
        order.setAddressDetail(address.getAddressDetail());
        order.setPhone(address.getPhone());
        order.setAddressDataId(address.getAddressId());

        BigDecimal totalPrice = new BigDecimal(0);

        //insert line item
        for (int i = 0, cartItemIdsLength = cartItemIds.length; i < cartItemIdsLength; i++) {
            int cartItemId = cartItemIds[i];
            CartItem cartItem = cartItemMapper.selectById(cartItemId);
            LineItem lineItem = new LineItem();
            lineItem.setOrderId(order.getOrderId());
            lineItem.setLineNumber(i + 1);
            lineItem.setQuantity(cartItem.getQuantity());
            lineItem.setItemId(cartItem.getItemId());
            Item item = itemMapper.selectById(cartItem.getItemId());
            lineItem.setUnitPrice(item.getListPrice());
            totalPrice = totalPrice.add(item.getListPrice().multiply(new BigDecimal(cartItem.getQuantity())));
            lineItemMapper.insert(lineItem);
        }
        order.setTotalPrice(totalPrice);
        orderMapper.insert(order);
        return order;
    }


    public int getNextId(String name) {
        Sequence sequence = sequenceMapper.selectById(name);
        if (sequence == null) {
            throw new RuntimeException("Error: A null sequence was returned from the database (could not get next " + name + " sequence).");
        }
        Sequence parameterObject = new Sequence(name, sequence.getNextId() + 1);
        sequenceMapper.update(parameterObject, null);
        return sequence.getNextId();
    }

}
