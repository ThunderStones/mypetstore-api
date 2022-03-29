package org.csu.mypetstore.api.service;

import org.csu.mypetstore.api.common.CommonResponse;
import org.csu.mypetstore.api.entity.Category;
import org.csu.mypetstore.api.entity.Product;
import org.csu.mypetstore.api.vo.ItemVO;

import java.util.List;

public interface CatalogService {
    List<Category> getCategoryList();
    Category getCategory(String categoryId);

    List<Product> getProductListByCategoryId(String categoryId);

    Product getProductById(String productId);

    List<ItemVO> getItemsByProductId(String productId);

    ItemVO getItemById(String itemId);

    List<Product> searchProduct(String keywords);
}
