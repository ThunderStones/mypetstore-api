package org.csu.mypetstore.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.csu.mypetstore.api.dto.CartItemDTO;
import org.csu.mypetstore.api.entity.Item;
import org.csu.mypetstore.api.persistence.*;
import org.csu.mypetstore.api.service.CatalogService;
import org.csu.mypetstore.api.vo.CartVO;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

    @Test
    void contextLoads() throws JsonProcessingException {
    }



}
