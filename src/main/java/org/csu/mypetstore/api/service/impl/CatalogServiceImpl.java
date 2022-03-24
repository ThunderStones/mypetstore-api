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
    public CommonResponse<Category> getCategory(String categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        if (category == null) {
            return CommonResponse.createForSuccessMessage("No Such Category With Id " + categoryId);
        }
        return CommonResponse.createForSuccess(category);
    }

    @Override
    public CommonResponse<List<Product>> getProductListByCategoryId(String categoryId) {
        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
        productQueryWrapper.eq("category", categoryId);
        List<Product> productList = productMapper.selectList(productQueryWrapper);
        if (productList.isEmpty()) {
            return CommonResponse.createForSuccessMessage("No Product In " + categoryId);
        }
        return CommonResponse.createForSuccess(productList);
    }

    @Override
    public CommonResponse<List<Category>> getCategoryList() {
        List<Category> categoryList = categoryMapper.selectList(null);
        if (categoryList.isEmpty()) {
            return CommonResponse.createForSuccessMessage("No Category Found");
        }
        return CommonResponse.createForSuccess(categoryList);
    }

    @Override
    public CommonResponse<Product> getProductById(String productId) {
        Product product = productMapper.selectById(productId);
        if (product == null) {
            return CommonResponse.createForSuccessMessage("No Product Found");
        }
        return CommonResponse.createForSuccess(product);
    }

    @Override
    public CommonResponse<List<ItemVO>> getItemsByProductId(String productId) {
        QueryWrapper<Item> itemQueryWrapper = new QueryWrapper<>();
        itemQueryWrapper.eq("productid", productId);
        List<Item> itemList = itemMapper.selectList(itemQueryWrapper);
        if (itemList.isEmpty()) {
            return CommonResponse.createForSuccessMessage("No Item Found");
        }
        List<ItemVO> itemVOList = new ArrayList<>();
        for (Item item: itemList) {
            itemVOList.add(itemToItemVO(item));
        }
        return CommonResponse.createForSuccess(itemVOList);
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
