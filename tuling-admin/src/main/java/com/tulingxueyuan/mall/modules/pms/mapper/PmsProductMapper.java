package com.tulingxueyuan.mall.modules.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tulingxueyuan.mall.dto.ProductUpdateInitDTO;
import com.tulingxueyuan.mall.modules.pms.model.PmsProduct;

/**
 * <p>
 * 商品信息 Mapper 接口
 * </p>
 *
 * @author ts
 * @since 2023-08-31
 */
public interface PmsProductMapper extends BaseMapper<PmsProduct> {

    ProductUpdateInitDTO getUpdateInfo(Long id);
}
