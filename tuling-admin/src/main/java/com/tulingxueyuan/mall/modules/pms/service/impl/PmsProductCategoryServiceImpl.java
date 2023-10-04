package com.tulingxueyuan.mall.modules.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.modules.pms.mapper.PmsProductCategoryMapper;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductCategory;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductCategoryAttributeRelation;
import com.tulingxueyuan.mall.modules.pms.model.dto.PmsProductCategoryDTO;
import com.tulingxueyuan.mall.modules.pms.model.dto.ProductCateChildrenDTO;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductCategoryAttributeRelationService;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 产品分类 服务实现类
 * </p>
 *
 * @author ts
 * @since 2023-08-31
 */
@Service
public class PmsProductCategoryServiceImpl extends ServiceImpl<PmsProductCategoryMapper, PmsProductCategory> implements PmsProductCategoryService {


    @Autowired
    PmsProductCategoryAttributeRelationService relationService;
    @Autowired
    PmsProductCategoryMapper productCategoryMapper;
    @Override
    public Page list(Long parentId, Integer pageNum, Integer pageSize) {
        Page page = new Page(pageNum,pageSize);

        //条件构造器
        QueryWrapper<PmsProductCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PmsProductCategory::getParentId,parentId).orderByAsc(PmsProductCategory::getSort);
        return this.page(page,queryWrapper);
    }

    //修改导航栏显示状态
    @Override
    public boolean updateNavStatus(List<Long> ids, Integer navStatus) {

        UpdateWrapper<PmsProductCategory> pmsProductCategoryUpdateWrapper = new UpdateWrapper<>();
        pmsProductCategoryUpdateWrapper.lambda()
                //需要更新的列
                .set(PmsProductCategory::getNavStatus,navStatus)
        //条件
                .in(PmsProductCategory::getId,ids);

        boolean update = this.update(pmsProductCategoryUpdateWrapper);
        return update;
    }

    //修改商品是否展示
    @Override
    public boolean updateShowStatus(List<Long> ids, Integer showStatus) {
        UpdateWrapper<PmsProductCategory> pmsProductCategoryUpdateWrapper = new UpdateWrapper<>();
        pmsProductCategoryUpdateWrapper.lambda()
                //需要更新的列
                .set(PmsProductCategory::getShowStatus,showStatus)
                //条件
                .in(PmsProductCategory::getId,ids);

        boolean update = this.update(pmsProductCategoryUpdateWrapper);
        return update;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean customeSave(PmsProductCategoryDTO productCategoryDTO) {

        Boolean isSave,isSave1;
        //保存商品分类
        PmsProductCategory productCategory = new PmsProductCategory();
        BeanUtils.copyProperties(productCategoryDTO,productCategory);
        //设置商品数量和级别
        productCategory.setProductCount(0);
        if (productCategory.getParentId() == 0){
            productCategory.setLevel(0);
        }else {
            productCategory.setLevel(1);
        }
        this.save(productCategory);

        //商品筛选属性
        saveAttrRelation(productCategoryDTO, productCategory);
        return true;
    }

    @Override
    public boolean update(PmsProductCategoryDTO productCategoryDTO) {
        //保存商品分类
        PmsProductCategory productCategory = new PmsProductCategory();
        BeanUtils.copyProperties(productCategoryDTO,productCategory);
        if (productCategory.getParentId() == 0){
            productCategory.setLevel(0);
        }else {
            productCategory.setLevel(1);
        }
        this.updateById(productCategory);

        //商品筛选属性
        saveAttrRelation(productCategoryDTO, productCategory);

        //删除已保存的关联属性
        QueryWrapper<PmsProductCategoryAttributeRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PmsProductCategoryAttributeRelation::getProductCategoryId,productCategory.getId());
        relationService.remove(queryWrapper);
        saveAttrRelation(productCategoryDTO,productCategory);
        return true;
    }

    @Override
    public List<ProductCateChildrenDTO> getWithChildren() {
        return productCategoryMapper.getWithChildren();
    }




    private Boolean saveAttrRelation(PmsProductCategoryDTO productCategoryDTO, PmsProductCategory productCategory) {
        List<Long> productAttributeIdList = productCategoryDTO.getProductAttributeIdList();
        List<PmsProductCategoryAttributeRelation> list = new ArrayList<>();
        for (Long attrId : productAttributeIdList) {
            //得到分类保存后的主键id，保存商品分类筛选属性关系
            PmsProductCategoryAttributeRelation productCategoryAttributeRelation = new PmsProductCategoryAttributeRelation();
            productCategoryAttributeRelation.setProductCategoryId(productCategory.getId());
            productCategoryAttributeRelation.setProductAttributeId(attrId);
            list.add(productCategoryAttributeRelation);
        }
        return relationService.saveBatch(list);
    }
}
