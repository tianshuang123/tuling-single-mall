package com.tulingxueyuan.mall.modules.pms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductCategory;
import com.tulingxueyuan.mall.modules.pms.model.dto.PmsProductCategoryDTO;
import com.tulingxueyuan.mall.modules.pms.model.dto.ProductCateChildrenDTO;

import java.util.List;

/**
 * <p>
 * 产品分类 服务类
 * </p>
 *
 * @author ts
 * @since 2023-08-31
 */
public interface PmsProductCategoryService extends IService<PmsProductCategory> {

    //获取商品分类列表
    Page list(Long parentId, Integer pageNum, Integer pageSize);

    //修改导航栏显示状态
    boolean updateNavStatus(List<Long> ids, Integer navStatus);

    boolean updateShowStatus(List<Long> ids, Integer showStatus);

    boolean customeSave(PmsProductCategoryDTO productCategoryDTO);

    boolean update(PmsProductCategoryDTO productCategoryDTO);

    List<ProductCateChildrenDTO> getWithChildren();
}
