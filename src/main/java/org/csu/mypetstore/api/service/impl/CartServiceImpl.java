package org.csu.mypetstore.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.csu.mypetstore.api.entity.CartItem;
import org.csu.mypetstore.api.entity.Item;
import org.csu.mypetstore.api.persistence.CartItemMapper;
import org.csu.mypetstore.api.persistence.InventoryMapper;
import org.csu.mypetstore.api.persistence.ItemMapper;
import org.csu.mypetstore.api.service.CartService;

import org.csu.mypetstore.api.vo.CartItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("cartService")
public class CartServiceImpl implements CartService {

    @Autowired
    private InventoryMapper inventoryMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private CartItemMapper cartItemMapper;

    @Override
    public List<CartItemVO> getCartItemsByUsername(String username) {
        QueryWrapper<CartItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        List<CartItem> cartItemList = cartItemMapper.selectList(queryWrapper);
        List<CartItemVO> cartItemVOList = new ArrayList<>();
        for (CartItem cartItem : cartItemList) {
            CartItemVO cartItemVO = new CartItemVO();
            cartItemVO.setId(cartItem.getId());
            cartItemVO.setItemId(cartItem.getItemId());
            cartItemVO.setQuantity(cartItem.getQuantity());
            Item item = itemMapper.selectById(cartItem.getItemId());
            cartItemVO.setUnitPrice(item.getListPrice());
            cartItemVO.setInStock(inventoryMapper.selectById(item.getItemId()).getQuantity() > 0);
            cartItemVO.calculateTotal();
            cartItemVOList.add(cartItemVO);
        }
        return cartItemVOList;
    }

    @Override
    public void addItem(String username, String itemId, int quantity) {
        CartItem cartItem = new CartItem();
        cartItem.setUsername(username);
        cartItem.setItemId(itemId);
        cartItem.setQuantity(Math.min(quantity, 200));
        QueryWrapper<CartItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        queryWrapper.eq("itemId", itemId);
        if (cartItemMapper.selectOne(queryWrapper) == null) {
            cartItemMapper.insert(cartItem);
        } else {
            cartItemMapper.update(cartItem, queryWrapper);
        }
    }


    @Override
    public void removeItem(String username, String itemId) {
        QueryWrapper<CartItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        queryWrapper.eq("itemId", itemId);
        cartItemMapper.delete(queryWrapper);
    }

    @Override
    public void clearCart(String username) {
        QueryWrapper<CartItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        cartItemMapper.delete(queryWrapper);
    }

    @Override
    public void clearCart(int[] cartItemIds) {
        for (int cartItemId : cartItemIds) {
            cartItemMapper.deleteById(cartItemId);
        }
    }

    @Override
    public List<CartItem> cartItemVOToCartItem(String username, List<CartItemVO> cartItemList) {
        List<CartItem> cartItemList1 = new ArrayList<>();
        for (CartItemVO cartItemVO : cartItemList) {
            CartItem cartItem1 = new CartItem();
            cartItem1.setUsername(username);
            cartItem1.setItemId(cartItemVO.getItemId());
            cartItem1.setQuantity(cartItemVO.getQuantity());
            cartItemList1.add(cartItem1);
        }
        return cartItemList1;
    }

    @Override
    public void addItem(CartItem cartItem) {
        addItem(cartItem.getUsername(), cartItem.getItemId(), cartItem.getQuantity());
    }

    @Override
    public void removeItem(CartItem cartItem) {
        removeItem(cartItem.getUsername(), cartItem.getItemId());
    }
}
