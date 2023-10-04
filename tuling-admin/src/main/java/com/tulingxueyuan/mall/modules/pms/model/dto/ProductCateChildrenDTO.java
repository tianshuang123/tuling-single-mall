package com.tulingxueyuan.mall.modules.pms.model.dto;/**
 * @title ProductCateChildrenDTO
 * @date 2023/9/11 21:58
 * @author ts happy boy
 * @description TODO
 */

import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttribute;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductCategory;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @BelongsProject: tuling-single-mall
 * @BelongsPackage: com.tulingxueyuan.mall.modules.pms.model.dto
 * @Author: ts  happyBoy
 * @CreateTime: 2023-09-11  21:58
 * @Description: TODO
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ProductCateChildrenDTO商品一级分类和二级分类的级联", description="商品列表--商品列表下拉框")
public class ProductCateChildrenDTO {
    // 商品分类id
    private Long id;

    // 商品分类名称
    private String name;

    // 商品分类二级级联
    private List<PmsProductCategory> children;
}



