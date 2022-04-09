package org.csu.mypetstore.api.controller.controller;

import org.csu.mypetstore.api.common.CommonResponse;
import org.csu.mypetstore.api.dto.OrderDTO;
import org.csu.mypetstore.api.entity.Order;
import org.csu.mypetstore.api.service.CartService;
import org.csu.mypetstore.api.service.OrderService;
import org.csu.mypetstore.api.vo.AccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @GetMapping("/list")
    public CommonResponse<List<Order>> getOrderList(HttpServletRequest request) {
        AccountVO accountVO = (AccountVO) request.getAttribute("account");
        return CommonResponse.createForSuccess(orderService.getOrderList(accountVO.getUsername()));
    }

    @GetMapping("/detail")
    public CommonResponse<OrderDTO> getOrderDetail(HttpServletRequest request, String orderId) {
        AccountVO accountVO = (AccountVO) request.getAttribute("account");
        return CommonResponse.createForSuccess(orderService.getOrder(orderId));
    }

    @PostMapping("")
    public CommonResponse<Order> placeOrder(HttpServletRequest request, int addressId ,@RequestBody int[] cartItemIds) {
        AccountVO accountVO = (AccountVO) request.getAttribute("account");
        Order order = orderService.placeOrder(accountVO.getUsername(),addressId ,cartItemIds);
        cartService.clearCart(cartItemIds);
        return CommonResponse.createForSuccess(order);
//        System.out.println(addressId);
//        System.out.println(Arrays.toString(cartItemIds));
//        return null;
    }
}
