package com.tulingxueyuan.mall.modules.pms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.dto.ProductUpdateInitDTO;
import com.tulingxueyuan.mall.modules.pms.model.PmsProduct;
import com.tulingxueyuan.mall.modules.pms.model.dto.ProductConditionDTO;
import com.tulingxueyuan.mall.modules.pms.model.dto.ProductSaveParamsDTO;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 商品信息 前端控制器
 * </p>
 *
 * @author ts
 * @since 2023-08-31
 */
@RestController
@RequestMapping("/product")
public class PmsProductController {
    @Autowired
    PmsProductService productService;

    /*    keyword: null,
    pageNum: 1,
    pageSize: 5,
    publishStatus: null,
    verifyStatus: null,
    productSn: null,
    productCategoryId: null,
    brandId: null
    url:'/product/list',
    method:'get',
    params:params
    * */
    @ApiOperation("商品列表--数据列表")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public CommonResult list(ProductConditionDTO condition) {

        Page page = productService.list(condition);
        return CommonResult.success(CommonPage.restPage(page));
    }


    /*
    *  url:'/product/update/deleteStatus',
    method:'post',
    params:params
    * */
    @ApiOperation("商品列表--逻辑删除")
    @RequestMapping(value = "/update/deleteStatus",method = RequestMethod.POST)
    public CommonResult deletye(@RequestParam("ids") List<Long> ids) {

        boolean result = productService.removeByIds(ids);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }
    /*
    url:'/product/update/newStatus',
    method:'post',
    params:params
    * */

    @ApiOperation("商品列表--新品状态")
    @RequestMapping(value = "/update/newStatus",method = RequestMethod.POST)
    public CommonResult updateNewStatus(@RequestParam("ids") List<Long> ids,
                                        @RequestParam("newStatus") Integer newStatus) {

        boolean result = productService.updateStatus(newStatus,ids,PmsProduct::getNewStatus);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    /*
    *  url:'/product/update/publishStatus',
    method:'post',
    params:params
    * */

    @ApiOperation("商品列表--上架状态")
    @RequestMapping(value = "/update/publishStatus",method = RequestMethod.POST)
    public CommonResult updatePublishStatus(@RequestParam("ids") List<Long> ids,
                                        @RequestParam("publishStatus") Integer publishStatus) {

        boolean result = productService.updateStatus(publishStatus,ids,PmsProduct::getPublishStatus);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    /*
        url:'/product/update/recommendStatus',
        method:'post',
        params:params
    * */
    @ApiOperation("商品列表--推荐状态")
    @RequestMapping(value = "/update/recommendStatus",method = RequestMethod.POST)
    public CommonResult updateRecommendStatus(@RequestParam("ids") List<Long> ids,
                                        @RequestParam("recommendStatus") Integer recommendStatus) {

        boolean result = productService.updateStatus(recommendStatus,ids,PmsProduct::getRecommandStatus);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    /*
    * url:'/product/create',
    method:'post',
    data:data
    * */
    @ApiOperation("添加商品")
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public CommonResult create(@RequestBody ProductSaveParamsDTO productSaveParamsDTO){
        boolean result  = productService.create(productSaveParamsDTO);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

//    url:'/product/updateInfo/'+id,
//    method:'get',

    @ApiOperation("编辑商品")
    @RequestMapping(value = "/updateInfo/{id}",method = RequestMethod.GET)
    public CommonResult updateInfo(@PathVariable Long id){
        ProductUpdateInitDTO productUpdateInitDTO = productService.updateInfo(id);
       return CommonResult.success(productUpdateInitDTO);
    }

    /*
    *  url:'/product/update/'+id,
    method:'post',
    data:data
    * */
    @ApiOperation("编辑商品中的提交")
    @RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
    public CommonResult update(@RequestBody @Valid ProductSaveParamsDTO productSaveParamsDTO){
        boolean result  = productService.update(productSaveParamsDTO);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }



}

