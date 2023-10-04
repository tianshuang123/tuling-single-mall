package com.tulingxueyuan.mall.modules.pms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.model.PmsBrand;
import com.tulingxueyuan.mall.modules.pms.model.dto.ProductCateChildrenDTO;
import com.tulingxueyuan.mall.modules.pms.service.PmsBrandService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 品牌表 前端控制器
 * </p>
 *
 * @author ts
 * @since 2023-08-31
 */
@RestController
@RequestMapping("/brand")
public class PmsBrandController {
    @Autowired
    PmsBrandService pmsBrandService;

    /*
    品牌数据列表在商品中进行共用
    * url:'/brand/list',
    method:'get',
    params:params
    * */
    @ApiOperation("商品列表--商品品牌下拉框")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public CommonResult list(@RequestParam (value = "keyword",defaultValue = "") String keyword,
                             @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize){

        Page page= pmsBrandService.list(keyword,pageNum,pageSize);
        return CommonResult.success(CommonPage.restPage(page));

    }

}

