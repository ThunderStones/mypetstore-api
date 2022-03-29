package org.csu.mypetstore.api.controller.controller;

import org.csu.mypetstore.api.annotation.PassToken;
import org.csu.mypetstore.api.common.CommonResponse;
import org.csu.mypetstore.api.common.ResponseCode;
import org.csu.mypetstore.api.entity.Category;
import org.csu.mypetstore.api.entity.Product;
import org.csu.mypetstore.api.service.CatalogService;
import org.csu.mypetstore.api.vo.ItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @GetMapping("/categories")
    @PassToken
    public CommonResponse<List<Category>> getCategoryList() {
        List<Category> categories = catalogService.getCategoryList();
        return CommonResponse.createForSuccess(categories);
    }

    @GetMapping("/categories/{id}")
    @PassToken
    public CommonResponse<Category> getCategory(@PathVariable("id") String categoryId) {
        Category category = catalogService.getCategory(categoryId);
        if (category == null) {
            return CommonResponse.createForError(ResponseCode.NO_DATA_FOUND.getCode(), String.format("Category %s not found", categoryId));
        } else {
            return CommonResponse.createForSuccess(category);
        }
    }

    @GetMapping("/categories/{id}/products")
    @PassToken
    public CommonResponse<List<Product>> getProductsByCategoryId(@PathVariable("id") String categoryId) {
        List<Product> products = catalogService.getProductListByCategoryId(categoryId);
        if (products == null) {
            return CommonResponse.createForError(ResponseCode.NO_DATA_FOUND.getCode(), String.format("Category %s not found", categoryId));
        } else {
            return CommonResponse.createForSuccess(products);
        }
    }

    @GetMapping("/products/{id}")
    @PassToken
    public CommonResponse<Product> getProductById(@PathVariable("id") String productId) {
        Product product = catalogService.getProductById(productId);
        if (product == null) {
            return CommonResponse.createForError(ResponseCode.NO_DATA_FOUND.getCode(), String.format("Product %s not found", productId));
        } else {
            return CommonResponse.createForSuccess(product);
        }
    }

    @GetMapping("/products/{id}/items")
    @PassToken
    public CommonResponse<List<ItemVO>> getItemByProductId(@PathVariable("id") String productId) {
        List<ItemVO> items = catalogService.getItemsByProductId(productId);
        if (items.size() == 0) {
            return CommonResponse.createForError(ResponseCode.NO_DATA_FOUND.getCode(), String.format("Item not found in %s", productId));
        } else {
            return CommonResponse.createForSuccess(items);
        }
    }

    @GetMapping("/items/{id}")
    @PassToken
    public CommonResponse<ItemVO> getItemById(@PathVariable("id") String itemId) {
        ItemVO item = catalogService.getItemById(itemId);
        if (item == null) {
            return CommonResponse.createForError(ResponseCode.NO_DATA_FOUND.getCode(), String.format("Item %s not found", itemId));
        } else {
            return CommonResponse.createForSuccess(item);
        }
    }

    @GetMapping("/search/{keywords}")
    @PassToken
    public CommonResponse<List<Product>> searchProduct(@PathVariable("keywords") String keyword) {
        List<Product> products = catalogService.searchProduct(keyword);
        if (products.size() == 0) {
            return CommonResponse.createForError(ResponseCode.NO_DATA_FOUND.getCode(), String.format("Product not found in %s", keyword));
        } else {
            return CommonResponse.createForSuccess(products);
        }
    }

}
