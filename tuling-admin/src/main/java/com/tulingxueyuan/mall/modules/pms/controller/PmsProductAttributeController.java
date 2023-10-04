package com.tulingxueyuan.mall.modules.pms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttribute;
import com.tulingxueyuan.mall.modules.pms.model.dto.RelationAttrInfoDTO;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductAttributeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 商品属性参数表 前端控制器
 * </p>
 *
 * @author ts
 * @since 2023-08-31
 */
@RestController
@RequestMapping("/productAttribute")
public class PmsProductAttributeController {
    @Autowired
    PmsProductAttributeService productAttributeService;

//    url:'/productAttribute/list/'+cid,
//    method:'get',
//    params:params

    @ApiOperation("商品分类---商品属性列表")
    @RequestMapping(value = "/list/{cid}",method = RequestMethod.GET)
    public CommonResult<CommonPage<PmsProductAttribute>> getList(@PathVariable Long cid,@RequestParam(value = "type") Integer type,
                                                                         @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                                                         @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize){

        Page page = productAttributeService.list(cid,type,pageNum,pageSize);
        return CommonResult.success(CommonPage.restPage(page));
    }

//    url:'/productAttribute/create',
//    method:'post',
//    data:data

    @ApiOperation("商品分类---添加商品属性")
    @RequestMapping(value = "/create",method = RequestMethod.POST)
        public CommonResult create(@RequestBody PmsProductAttribute productAttribute){

        boolean result = productAttributeService.create(productAttribute);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }


//    url:'/productAttribute/update/'+id,
//    method:'post',
//    data:data

    @ApiOperation("商品分类---修改商品属性提交")
    @RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
    public CommonResult update(@PathVariable Long id,
                               @RequestBody PmsProductAttribute productAttribute){

        boolean result = productAttributeService.updateById(productAttribute);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

//    url:'/productAttribute/'+id,
//    method:'get'

    @ApiOperation("商品分类---商品属性列表初始化")
    @RequestMapping(value = "/{cid}",method = RequestMethod.GET)
    public CommonResult<PmsProductAttribute> getListAttribute(@PathVariable Long cid){

        PmsProductAttribute productAttribute = productAttributeService.getById(cid);
        return CommonResult.success(productAttribute);
    }

//    url:'/productAttribute/delete',
//    method:'post',

    @ApiOperation("商品分类---商品属性列表删除")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public CommonResult delete(@RequestParam("ids")List<Long> ids){

        boolean result = productAttributeService.delete(ids);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }

    /*
    * url:'/productAttribute/attrInfo/'+productCategoryId,
    * method:'get'
    * */
    @ApiOperation("商品分类--根据商品id获取关联的筛选属性")
    @RequestMapping(value = "attrInfo/{cid}",method = RequestMethod.GET)
    public CommonResult getRelationAttrInfoByCid(@PathVariable Long cid){

        List<RelationAttrInfoDTO> list= productAttributeService.getRelationAttrInfoByCid(cid);
        return CommonResult.success(list);
    }


}

