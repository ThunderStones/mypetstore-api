package org.csu.mypetstore.api.dto;

import org.csu.mypetstore.api.entity.Item;

import java.math.BigDecimal;
import java.util.*;

public class CartDTO implements java.io.Serializable {
    private final Map<String, CartItemDTO> itemMap = Collections.synchronizedMap(new HashMap<>());
    private final List<CartItemDTO> itemList = new ArrayList<>();

    public Iterator<CartItemDTO> getCartItems() {
        return itemList.iterator();
    }

    public List<CartItemDTO> getCartItemList() {
        return itemList;
    }

    public int getNumberOfItems() {
        return itemList.size();
    }

    public Iterator<CartItemDTO> getAllCartItems() {
        return itemList.iterator();
    }

    public boolean containsItemId(String itemId) {
        return itemMap.containsKey(itemId);
    }

    /**
     * Add item
     *
     * @param item      the Item
     * @param isInStock the item is in stock
     */
    public void addItem(Item item, boolean isInStock) {
        addItem(item, isInStock, 1);
    }

    public void addItem(Item item, boolean isInStock, int quantity) {
        CartItemDTO cartItem = itemMap.get(item.getItemId());
        if (cartItem == null) {
            cartItem = new CartItemDTO();
            cartItem.setItem(item);
            cartItem.setQuantity(0);
            cartItem.setInStock(isInStock);
            itemMap.put(item.getItemId(), cartItem);
            itemList.add(cartItem);
        }
        for (int i = 0; i < quantity; i++) {
            cartItem.incrementQuantity();
        }
    }

    /**
     * remove item by itemId
     *
     * @param itemId the itemId
     * @return the item
     */
    public Item removeItemById(String itemId) {
        CartItemDTO cartItem = itemMap.remove(itemId);
        if (cartItem == null) {
            return null;
        } else {
            itemList.remove(cartItem);
            return cartItem.getItem();
        }
    }

    /**
     * Increment quantity by item id
     *
     * @param itemId the item id
     */
    public void incrementQuantityByItemId(String itemId) {
        CartItemDTO cartItem = itemMap.get(itemId);
        cartItem.incrementQuantity();
    }

    public void incrementQuantityByItemId(String itemId, int quantity) {
        CartItemDTO cartItem = itemMap.get(itemId);
        cartItem.setQuantity(quantity);
    }

    public void setQuantityByItemId(String itemId, int quantity) {
        CartItemDTO cartItem = itemMap.get(itemId);
        cartItem.setQuantity(quantity);
    }

    /**
     * Get the subtotal
     *
     * @return the subtotal
     */
    public BigDecimal getSubTotal() {
        return itemList.stream()
                .map(cartItem -> cartItem.getItem().getListPrice().multiply(new BigDecimal(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
