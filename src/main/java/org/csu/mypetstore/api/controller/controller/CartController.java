package org.csu.mypetstore.api.controller.controller;

import org.csu.mypetstore.api.common.CommonResponse;
import org.csu.mypetstore.api.common.ResponseCode;
import org.csu.mypetstore.api.entity.CartItem;
import org.csu.mypetstore.api.service.CartService;
import org.csu.mypetstore.api.vo.AccountVO;
import org.csu.mypetstore.api.vo.CartItemVO;
import org.csu.mypetstore.api.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PutMapping("/items")
    public CommonResponse<String> addItems(@RequestBody List<CartItemVO> cartItems, HttpServletRequest request) throws IOException {
        AccountVO account = (AccountVO) request.getAttribute("account");
        if (cartItems == null || cartItems.size() == 0) {
            return CommonResponse.createForError(ResponseCode.ILLEGAL_ARGUMENT.getCode(), "cartItems is null");
        }
        cartService.cartItemVOToCartItem(account.getUsername(), cartItems).forEach(cartItem -> {
            cartService.addItem(cartItem);
        });
        return CommonResponse.createForSuccess("add items success");
    }

    @DeleteMapping("/items")
    public CommonResponse<String> removeItems(@RequestBody List<CartItemVO> cartItems, HttpServletRequest request) throws IOException {
        AccountVO account = (AccountVO) request.getAttribute("account");
        if (cartItems == null || cartItems.size() == 0) {
            return CommonResponse.createForError(ResponseCode.ILLEGAL_ARGUMENT.getCode(), "cartItems is null");
        }
        cartService.cartItemVOToCartItem(account.getUsername(), cartItems).forEach(cartItem -> {
            cartService.removeItem(cartItem);
        });
        return CommonResponse.createForSuccess("remove items success");
    }

    @DeleteMapping("/items/all")
    public CommonResponse<String> removeAllItems(HttpServletRequest request) throws IOException {
        AccountVO account = (AccountVO) request.getAttribute("account");
        cartService.clearCart(account.getUsername());
        return CommonResponse.createForSuccess("remove all items success");
    }

    @GetMapping("/items")
    public CommonResponse<List<CartItemVO>> getCartItems(HttpServletRequest request) throws IOException {
        AccountVO account = (AccountVO) request.getAttribute("account");
        List<CartItemVO> cartItems = cartService.getCartItemsByUsername(account.getUsername());
        return CommonResponse.createForSuccess(cartItems);
    }

}
