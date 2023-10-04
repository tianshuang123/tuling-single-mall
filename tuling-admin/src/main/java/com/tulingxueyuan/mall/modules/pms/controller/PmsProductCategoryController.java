package com.tulingxueyuan.mall.modules.pms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductCategory;
import com.tulingxueyuan.mall.modules.pms.model.dto.PmsProductCategoryDTO;
import com.tulingxueyuan.mall.modules.pms.model.dto.ProductCateChildrenDTO;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 产品分类 前端控制器
 * </p>
 *
 * @author ts
 * @since 2023-08-31
 */
@Api(tags = "PmsProductCategoryController", description = "后台商品分类")
@RestController
@RequestMapping("/productCategory")
public class PmsProductCategoryController {
    @Autowired
    PmsProductCategoryService productCategoryService;
    /** url:'/productCategory/list/'+parentId,
     method:'get',
     params:params
     */
    @ApiOperation("商品列表")
    @RequestMapping(value = "/list/{parentId}",method = RequestMethod.GET)
    public CommonResult<CommonPage<PmsProductCategory>> getList(@PathVariable Long parentId,
                                                                @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                                                @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize){

        Page page = productCategoryService.list(parentId,pageNum,pageSize);
        return CommonResult.success(CommonPage.restPage(page));
    }


    /*
    *
    * url:'/productCategory/update/showStatus',
    method:'post',
    data:data
    * */
    @ApiOperation("商品列表是否展示")
    @RequestMapping(value = "/update/showStatus",method = RequestMethod.POST)
    public CommonResult getList(@RequestParam(value = "ids") List<Long> ids,
                                         @RequestParam(value = "showStatus")Integer showStatus){

        boolean result = productCategoryService.updateShowStatus(ids, showStatus);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }


    /*
    url:'/productCategory/update/navStatus',
    method:'post',
    data:data
     **/

    @ApiOperation("商品列表导航栏")
    @RequestMapping(value = "/update/navStatus",method = RequestMethod.POST)
    public CommonResult updateNavStatus(@RequestParam(value = "ids") List<Long> ids,
                                                 @RequestParam(value = "navStatus")Integer navStatus){

        boolean result = productCategoryService.updateNavStatus(ids, navStatus);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }

    }


//    return request({
//        url:'/productCategory/delete/'+id,
//                method:'post'
//    })

    @ApiOperation("商品分类删除")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.POST)
    public CommonResult delete(@PathVariable Long id){

        boolean result = productCategoryService.removeById(id);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }

    }

//    url:'/productCategory/create',
//    method:'post',
//    data:data


    @ApiOperation("分类添加")
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public CommonResult create(@RequestBody PmsProductCategoryDTO productCategoryDTO){

        boolean result = productCategoryService.customeSave(productCategoryDTO);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

//    url:'/productCategory/'+id,
//    method:'get',

    @ApiOperation("修改商品分类")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public CommonResult<PmsProductCategory> getById(@PathVariable Long id){

        PmsProductCategory productCategory = productCategoryService.getById(id);
        return CommonResult.success(productCategory);

    }

//    url:'/productCategory/update/'+id,
//    method:'post',
//    data:data

    @ApiOperation("修改商品分类提交")
    @RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
    public CommonResult update(@PathVariable Long id,
                               @RequestBody PmsProductCategoryDTO productCategory){

        boolean result = productCategoryService.update(productCategory);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    /*
    * url:'/productCategory/list/withChildren',
    * method:'get'
    * */
    @ApiOperation("商品列表--商品列表下拉框")
    @RequestMapping(value = "/list/withChildren",method = RequestMethod.GET)
    public CommonResult getWithChildren(){

        List<ProductCateChildrenDTO> list= productCategoryService.getWithChildren();
        return CommonResult.success(list);

    }


}

