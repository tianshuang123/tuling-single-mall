package com.tulingxueyuan.mall.modules.pms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.dto.ProductUpdateInitDTO;
import com.tulingxueyuan.mall.modules.pms.mapper.PmsProductMapper;
import com.tulingxueyuan.mall.modules.pms.model.PmsProduct;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttribute;
import com.tulingxueyuan.mall.modules.pms.model.dto.ProductConditionDTO;
import com.tulingxueyuan.mall.modules.pms.model.dto.ProductSaveParamsDTO;
import com.tulingxueyuan.mall.modules.pms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * <p>
 * 商品信息 服务实现类
 * </p>
 *
 * @author ts
 * @since 2023-08-31
 */
@Service
public class PmsProductServiceImpl extends ServiceImpl<PmsProductMapper, PmsProduct> implements PmsProductService {

    @Autowired
    PmsProductMapper productMapper;
    @Autowired
    PmsMemberPriceService memberPriceService;

    @Autowired
    PmsProductLadderService productLadderService;

    @Autowired
    PmsProductFullReductionService productFullReductionService;

    @Autowired
    PmsSkuStockService skuStockService;

    @Autowired
    PmsProductAttributeValueService productAttributeValueService;

    @Override
    public Page list(ProductConditionDTO condition) {

        Page page = new Page(condition.getPageNum(),condition.getPageSize());

        QueryWrapper<PmsProduct> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<PmsProduct> lambdaWrapper = new LambdaQueryWrapper<>();
        //商品名称
        if (!StrUtil.isBlank(condition.getKeyword())){
            lambdaWrapper.like(PmsProduct::getName,condition.getKeyword());
        }
        //商品货号
        if (!StrUtil.isBlank(condition.getProductSn())){
            lambdaWrapper.eq(PmsProduct::getProductSn,condition.getProductSn());
        }
        //商品分类
        if (condition.getProductCategoryId() !=null){
            lambdaWrapper.eq(PmsProduct::getProductCategoryId,condition.getProductCategoryId());
        }
        //商品品牌
        if (condition.getBrandId() !=null){
            lambdaWrapper.eq(PmsProduct::getBrandId,condition.getBrandId());
        }
        //上架状态
        if (condition.getPublishStatus() !=null){
            lambdaWrapper.eq(PmsProduct::getPublishStatus,condition.getPublishStatus());
        }
        //审核状态
        if (condition.getVerifyStatus() !=null){
            lambdaWrapper.eq(PmsProduct::getVerifyStatus,condition.getVerifyStatus());
        }
        lambdaWrapper.orderByAsc(PmsProduct::getSort);
        return this.page(page,lambdaWrapper);
    }

    @Override
    public boolean updateStatus(Integer recommendStatus, List<Long> ids, SFunction<PmsProduct, ?> getPublishStatus) {
        UpdateWrapper<PmsProduct> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(getPublishStatus,recommendStatus).in(PmsProduct::getId,ids);
        boolean update = this.update(updateWrapper);
        return update;
    }

    @Override
    @Transactional
    public boolean create(ProductSaveParamsDTO productSaveParamsDTO) {
        //1.保存商品基本信息 --商品主表
        PmsProduct product = productSaveParamsDTO;
        product.setId(null);
        boolean result = this.save(product);
        if (result){
            switch (product.getPromotionType()){
                case 2:
                    //2.会员价格
                    saveManyList(productSaveParamsDTO.getMemberPriceList(), product.getId(), memberPriceService);
                    break;
                case 3:
                    //3.阶梯价格
                    saveManyList(productSaveParamsDTO.getProductLadderList(), product.getId(), productLadderService);
                    break;
                case 4:
                    //4.满减价格
                    saveManyList(productSaveParamsDTO.getProductFullReductionList(), product.getId(), productFullReductionService);
            }
            //5.sku
            saveManyList(productSaveParamsDTO.getSkuStockList(), product.getId(), skuStockService);
            //6.spu
            saveManyList(productSaveParamsDTO.getProductAttributeValueList(), product.getId(), productAttributeValueService);
        }

        return result;
    }

    @Override
    public ProductUpdateInitDTO updateInfo(Long id) {
        return productMapper.getUpdateInfo(id);
    }

    @Override
    @Transactional
    public Boolean update(ProductSaveParamsDTO productSaveParamsDTO) {
        //1.保存商品基本信息 --商品主表
        PmsProduct product = productSaveParamsDTO;
        boolean result = this.updateById(product);
        if (result){
            switch (product.getPromotionType()){
                case 2:
                    //2.会员价格
                    deleteManyListByProductId(product.getId(),memberPriceService);
                    saveManyList(productSaveParamsDTO.getMemberPriceList(), product.getId(),memberPriceService);
                    break;
                case 3:
                    //3.阶梯价格
                    deleteManyListByProductId(product.getId(),productLadderService);
                    saveManyList(productSaveParamsDTO.getProductLadderList(), product.getId(), productLadderService);
                    break;
                case 4:
                    //4.满减价格
                    deleteManyListByProductId(product.getId(), productFullReductionService);
                    saveManyList(productSaveParamsDTO.getProductFullReductionList(), product.getId(), productFullReductionService);
            }
            //5.sku
            deleteManyListByProductId(product.getId(), skuStockService);
            saveManyList(productSaveParamsDTO.getSkuStockList(), product.getId(), skuStockService);
            //6.spu
            deleteManyListByProductId(product.getId(), productAttributeValueService);
            saveManyList(productSaveParamsDTO.getProductAttributeValueList(), product.getId(), productAttributeValueService);
        }

        return result;
    }

    public void deleteManyListByProductId(Long productId,IService service){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("product_id",productId);

        service.remove(queryWrapper);
    }


    public void saveManyList(List list, Long productId, IService service) {
        //数据为空或者长度为0  不做任何操作
        if (CollectionUtil.isEmpty(list)){
            return;
        }
        try {
            for (Object obj : list) {
                Method method = obj.getClass().getMethod("setProductId", Long.class);

                //在修改状态下清楚主键id
                Method setId = obj.getClass().getMethod("setId", Long.class);
                setId.invoke(obj,(Long)null);

                method.invoke(obj,productId);

            }
            service.saveBatch(list);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

}
