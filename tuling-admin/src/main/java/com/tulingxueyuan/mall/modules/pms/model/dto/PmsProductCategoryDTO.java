package com.tulingxueyuan.mall.modules.pms.model.dto;/**
 * @title PmsProductCategoryDTO
 * @date 2023/9/10 22:11
 * @author ts happy boy
 * @description TODO
 */

import com.baomidou.mybatisplus.annotation.TableName;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductCategory;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @BelongsProject: tuling-single-mall
 * @BelongsPackage: com.tulingxueyuan.mall.modules.pms.model.dto
 * @Author: ts  happyBoy
 * @CreateTime: 2023-09-10  22:11
 * @Description: TODO
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="PmsProductCategoryDTO商品分类的传输数据对象对象", description="用于商品产品分类添加、修改数据接收")
public class PmsProductCategoryDTO extends PmsProductCategory {
    private List<Long> productAttributeIdList;
}



