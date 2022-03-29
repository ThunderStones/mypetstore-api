package org.csu.mypetstore.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import org.csu.mypetstore.api.common.CommonResponse;
import org.csu.mypetstore.api.entity.Category;
import org.csu.mypetstore.api.entity.Inventory;
import org.csu.mypetstore.api.entity.Item;
import org.csu.mypetstore.api.entity.Product;
import org.csu.mypetstore.api.persistence.CategoryMapper;
import org.csu.mypetstore.api.persistence.InventoryMapper;
import org.csu.mypetstore.api.persistence.ItemMapper;
import org.csu.mypetstore.api.persistence.ProductMapper;
import org.csu.mypetstore.api.service.CatalogService;
import org.csu.mypetstore.api.vo.ItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("CatalogService")
public class CatalogServiceImpl implements CatalogService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private InventoryMapper inventoryMapper;

    @Override
    public Category getCategory(String categoryId) {
        return categoryMapper.selectById(categoryId);
    }

    @Override
    public List<Product> getProductListByCategoryId(String categoryId) {
        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
        productQueryWrapper.eq("category", categoryId);
        return productMapper.selectList(productQueryWrapper);
    }

    @Override
    public List<Category> getCategoryList() {
        return categoryMapper.selectList(null);
    }

    @Override
    public Product getProductById(String productId) {
        return productMapper.selectById(productId);
    }

    @Override
    public List<ItemVO> getItemsByProductId(String productId) {
        QueryWrapper<Item> itemQueryWrapper = new QueryWrapper<>();
        itemQueryWrapper.eq("productid", productId);
        List<Item> itemList = itemMapper.selectList(itemQueryWrapper);
        List<ItemVO> itemVOList = new ArrayList<>();
        for (Item item: itemList) {
            itemVOList.add(itemToItemVO(item));
        }
        return itemVOList;
    }

    @Override
    public ItemVO getItemById(String itemId) {
        Item item = itemMapper.selectById(itemId);
        return itemToItemVO(item);
    }

    @Override
    public List<Product> searchProduct(String keywords) {
        List<Product> productList = new ArrayList<>();
        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
        for (String keyword : keywords.split("\\s+")) {
            productQueryWrapper.like("name", keyword).or().like("descn", keyword);
            productList.addAll(productMapper.selectList(productQueryWrapper));
        }
        return productList;
    }

    private ItemVO itemToItemVO(Item item) {
        ItemVO itemVO = new ItemVO();
        itemVO.setItemId(item.getItemId());
        itemVO.setProductId(item.getProductId());
        itemVO.setListPrice(item.getListPrice());
        itemVO.setUnitCost(item.getUnitCost());
        itemVO.setSupplierId(item.getSupplierId());
        itemVO.setStatus(item.getStatus());
        itemVO.setAttribute1(item.getAttribute1());
        itemVO.setAttribute2(item.getAttribute2());
        itemVO.setAttribute3(item.getAttribute3());
        itemVO.setAttribute4(item.getAttribute4());
        itemVO.setAttribute5(item.getAttribute5());

        Product product = productMapper.selectById(item.getProductId());
        itemVO.setProduct(product);

        Inventory inventory = inventoryMapper.selectById(item.getItemId());
        itemVO.setQuantity(inventory.getQuantity());
        return itemVO;
    }


}
