package com.tulingxueyuan.mall.modules.pms.service;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tulingxueyuan.mall.dto.ProductUpdateInitDTO;
import com.tulingxueyuan.mall.modules.pms.model.PmsProduct;
import com.tulingxueyuan.mall.modules.pms.model.dto.ProductConditionDTO;
import com.tulingxueyuan.mall.modules.pms.model.dto.ProductSaveParamsDTO;

import java.util.List;

/**
 * <p>
 * 商品信息 服务类
 * </p>
 *
 * @author ts
 * @since 2023-08-31
 */
public interface PmsProductService extends IService<PmsProduct> {

    Page list(ProductConditionDTO condition);


    boolean updateStatus(Integer recommendStatus, List<Long> ids, SFunction<PmsProduct,?> getPublishStatus);

    boolean create(ProductSaveParamsDTO productSaveParamsDTO);

    ProductUpdateInitDTO updateInfo(Long id);

    Boolean update(ProductSaveParamsDTO productSaveParamsDTO);
}
