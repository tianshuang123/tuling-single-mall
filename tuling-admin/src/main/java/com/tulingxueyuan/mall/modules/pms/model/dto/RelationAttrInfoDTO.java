package com.tulingxueyuan.mall.modules.pms.model.dto;/**
 * @title RelationAttrInfo
 * @date 2023/9/11 20:44
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
 * @CreateTime: 2023-09-11  20:44
 * @Description: TODO
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="RelationAttrInfoDTO分类和属性筛选关联数据", description="用于商品产品分类和属性筛选关联数据已保存数据的初始化")
public class RelationAttrInfoDTO {
    //商品类型id
    private Long attributeCategoryId;

    //商品双属性id
    private Long attributeId;
}



