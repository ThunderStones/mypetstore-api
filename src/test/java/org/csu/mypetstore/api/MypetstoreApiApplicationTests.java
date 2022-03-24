package org.csu.mypetstore.api;

import org.csu.mypetstore.api.persistence.CategoryMapper;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MypetstoreApiApplicationTests {
    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    void contextLoads() {
        System.out.println(categoryMapper.selectList(null).toString());
    }

}
