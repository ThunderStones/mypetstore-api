package org.csu.mypetstore.api.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CartVO {
    private String username;
    private List<CartItemVO> cartItemList = new ArrayList<>();
}
