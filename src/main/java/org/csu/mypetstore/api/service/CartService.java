package org.csu.mypetstore.api.service;

import org.csu.mypetstore.api.entity.CartItem;
import org.csu.mypetstore.api.vo.CartItemVO;

import java.util.List;

public interface CartService {
    List<CartItemVO> getCartItemsByUsername(String username);

    void addItem(String username, String itemId, int quantity);

    void removeItem(String username, String itemId);

    void clearCart(String username);

    void clearCart(int[] cartItemIds);

    List<CartItem> cartItemVOToCartItem(String username, List<CartItemVO> cartItemList);

    void addItem(CartItem cartItem);

    void removeItem(CartItem cartItem);
}
