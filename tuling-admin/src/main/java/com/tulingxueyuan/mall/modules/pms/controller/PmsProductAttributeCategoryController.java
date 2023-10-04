package com.tulingxueyuan.mall.modules.pms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttribute;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttributeCategory;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductCategory;
import com.tulingxueyuan.mall.modules.pms.model.dto.PmsProductCategoryDTO;
import com.tulingxueyuan.mall.modules.pms.model.dto.ProductAttributeDTO;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductAttributeCategoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 产品属性分类表 前端控制器
 * </p>
 *
 * @author ts
 * @since 2023-08-31
 */
@RestController
@RequestMapping("/productAttribute/category")
public class PmsProductAttributeCategoryController {
    /*
    *  url:'/productAttribute/category/list',
    method:'get',
    params:params
    * */

    @Autowired
    PmsProductAttributeCategoryService pmsProductAttributeCategoryService;
    @ApiOperation("商品类型列表")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public CommonResult<CommonPage<PmsProductAttributeCategory>> getList(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                                                 @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize){

        Page page = pmsProductAttributeCategoryService.list(pageNum,pageSize);
        return CommonResult.success(CommonPage.restPage(page));
    }


//    url:'/productAttribute/category/create',
//    method:'post',
//    data:data

    @ApiOperation("商品类型添加")
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public CommonResult create(PmsProductAttributeCategory productAttributeCategory){

        boolean result = pmsProductAttributeCategoryService.save(productAttributeCategory);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

//    url:'/productAttribute/category/update/'+id,
//    method:'post',
//    data:data

    @ApiOperation("商品类型更改")
    @RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
    public CommonResult update(PmsProductAttributeCategory productAttributeCategory){

        boolean result = pmsProductAttributeCategoryService.updateById(productAttributeCategory);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }


//    url:'/productAttribute/category/delete/'+id,
//    method:'get'

    @ApiOperation("商品类型删除")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    public CommonResult delete(PmsProductAttributeCategory productAttributeCategory){

        boolean result = pmsProductAttributeCategoryService.removeById(productAttributeCategory);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

//    url:'/productAttribute/category/list/withAttr',
//    method:'get'
    @ApiOperation("筛选属性下拉级联数据")
    @RequestMapping(value = "/list/withAttr",method = RequestMethod.GET)
    public CommonResult getListWithAttr(PmsProductAttributeCategory productAttributeCategory){
        List<ProductAttributeDTO> list = pmsProductAttributeCategoryService.getListWithAttr();
        return CommonResult.success(list);


    }


}

