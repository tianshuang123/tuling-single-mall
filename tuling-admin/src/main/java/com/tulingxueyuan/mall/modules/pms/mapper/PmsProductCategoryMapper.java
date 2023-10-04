package com.tulingxueyuan.mall.modules.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductCategory;
import com.tulingxueyuan.mall.modules.pms.model.dto.ProductCateChildrenDTO;

import java.util.List;

/**
 * <p>
 * 产品分类 Mapper 接口
 * </p>
 *
 * @author ts
 * @since 2023-08-31
 */
public interface PmsProductCategoryMapper extends BaseMapper<PmsProductCategory> {

    List<ProductCateChildrenDTO> getWithChildren();
}
