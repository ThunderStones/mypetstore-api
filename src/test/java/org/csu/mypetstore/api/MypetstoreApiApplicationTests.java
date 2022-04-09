package org.csu.mypetstore.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.csu.mypetstore.api.persistence.*;
import org.csu.mypetstore.api.service.AddressService;
import org.csu.mypetstore.api.service.CatalogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;

@SpringBootTest
class MypetstoreApiApplicationTests {
    @Autowired
    private LineItemMapper lineItemMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderStatusMapper orderStatusMapper;
    @Autowired
    private SequenceMapper sequenceMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private CatalogService catalogService;
    @Autowired
    private AddressService addressService;

    @Test
    void contextLoads() throws JsonProcessingException, FileNotFoundException {
//        System.out.println(addressDataService.getProvinceList());
//        System.out.println(addressDataService.getCityList("33"));
//        System.out.println(addressDataService.getDistrictList("33", "10"));
    }



}
