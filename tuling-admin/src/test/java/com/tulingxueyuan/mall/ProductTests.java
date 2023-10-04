package com.tulingxueyuan.mall;
/**
 * @title com.tulingxueyuan.mail.ProductTests
 * @date 2023/9/6 22:59
 * @author ts happy boy
 * @description TODO
 */

import com.tulingxueyuan.mall.modules.pms.mapper.PmsProductAttributeCategoryMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @BelongsProject: tuling-single-mall
 * @BelongsPackage: PACKAGE_NAME
 * @Author: ts  happyBoy
 * @CreateTime: 2023-09-06  22:59
 * @Description: TODO
 * @Version: 1.0
 */
@SpringBootTest
public class ProductTests {

    @Autowired
    PmsProductAttributeCategoryMapper mapper;
    @Test
    public void test01(){
        System.out.println(mapper.getListWithAttr());
    }
}



