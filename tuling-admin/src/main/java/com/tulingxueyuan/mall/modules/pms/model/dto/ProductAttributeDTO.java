package com.tulingxueyuan.mall.modules.pms.model.dto;/**
 * @title ProductAttributeDTO
 * @date 2023/9/6 22:24
 * @author ts happy boy
 * @description TODO
 */

import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttribute;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @BelongsProject: tuling-single-mall
 * @BelongsPackage: com.tulingxueyuan.mall.modules.pms.model.dto
 * @Author: ts  happyBoy
 * @CreateTime: 2023-09-06  22:24
 * @Description: TODO
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ProductAttributeDTO筛选属性数据传输对象", description="用于商品分类--属性筛选下拉级联")
public class ProductAttributeDTO {
    private Long id;

    private String name;

    private List<PmsProductAttribute> productAttributeList;
}



