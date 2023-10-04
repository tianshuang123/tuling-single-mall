package com.tulingxueyuan.mall.modules.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.modules.pms.mapper.PmsProductAttributeMapper;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttribute;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttributeCategory;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductCategory;
import com.tulingxueyuan.mall.modules.pms.model.dto.RelationAttrInfoDTO;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductAttributeCategoryService;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductAttributeService;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 商品属性参数表 服务实现类
 * </p>
 *
 * @author ts
 * @since 2023-08-31
 */
@Service
public class PmsProductAttributeServiceImpl extends ServiceImpl<PmsProductAttributeMapper, PmsProductAttribute> implements PmsProductAttributeService {
    @Autowired
    PmsProductAttributeCategoryService productAttributeCategoryService;

    @Autowired
    PmsProductAttributeMapper productAttributeMapper;

    @Override
    public Page list(Long cid, Integer type,Integer pageNum, Integer pageSize) {

        Page page = new Page(pageNum,pageSize);

        //条件构造器
        QueryWrapper<PmsProductAttribute> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PmsProductAttribute::getProductAttributeCategoryId,cid)
                .eq(PmsProductAttribute::getType,type).orderByAsc(PmsProductAttribute::getSort);
        return this.page(page,queryWrapper);
    }

    @Override
    @Transactional
    public boolean create(PmsProductAttribute productAttribute) {
        //保存商品属性
        boolean save = this.save(productAttribute);
        UpdateWrapper<PmsProductAttributeCategory> updateWrapper = new UpdateWrapper<>();
        if (save){
            //更新对应的属性、参数数量
            if (productAttribute.getType() == 0){
                updateWrapper.setSql("attribute_count=attribute_count+1");
            }
            else if (productAttribute.getType() == 1){
                updateWrapper.setSql("param_count=param_count+1");
            }
            updateWrapper.lambda().eq(PmsProductAttributeCategory::getId,productAttribute.getProductAttributeCategoryId());
            productAttributeCategoryService.update(updateWrapper);
        }
        return save;
    }

    @Override
    @Transactional
    public boolean delete(List<Long> ids) {
       //判断ids是否为空
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        //得到当前属性的类别
        PmsProductAttribute productAttribute = null;
        for (Long id : ids) {
            productAttribute = this.getById(id);
            if (productAttribute != null){
                break;
            }
        }
        int length = productAttributeMapper.deleteBatchIds(ids);
        if (length > 0 && productAttribute != null){
        UpdateWrapper<PmsProductAttributeCategory> updateWrapper = new UpdateWrapper<>();
            //更新对应的属性、参数数量
            if (productAttribute.getType() == 0){
                updateWrapper.setSql("attribute_count=attribute_count-"+length);
            }
            else if (productAttribute.getType() == 1){
                updateWrapper.setSql("param_count=param_count-"+length);
            }
            updateWrapper.lambda().eq(PmsProductAttributeCategory::getId,productAttribute.getProductAttributeCategoryId());
            productAttributeCategoryService.update(updateWrapper);
        }

        return length>0;
    }

    @Override
    public List<RelationAttrInfoDTO> getRelationAttrInfoByCid(Long cid) {
        return productAttributeMapper.getRelationAttrInfoByCid(cid);
    }
}
