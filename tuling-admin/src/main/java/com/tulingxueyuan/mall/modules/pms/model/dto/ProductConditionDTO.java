package com.tulingxueyuan.mall.modules.pms.model.dto;/**
 * @title ProductConditionDTO
 * @date 2023/9/12 21:00
 * @author ts happy boy
 * @description TODO
 */

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @BelongsProject: tuling-single-mall
 * @BelongsPackage: com.tulingxueyuan.mall.modules.pms.model.dto
 * @Author: ts  happyBoy
 * @CreateTime: 2023-09-12  21:00
 * @Description: TODO
 * @Version: 1.0
 */

/*
*
* keyword: null,
    pageNum: 1,
    pageSize: 5,
    publishStatus: null,
    verifyStatus: null,
    productSn: null,
    productCategoryId: null,
    brandId: null*/
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="商品列表--数据列表分类DTO", description="商品列表--数据列表分类列表的初始化")
public class ProductConditionDTO {
    private String keyword;
    private Integer pageNum;
    private Integer pageSize;
    private Integer publishStatus;
    private Integer verifyStatus;
    private String productSn;
    private Integer productCategoryId;
    private Integer brandId;
}



