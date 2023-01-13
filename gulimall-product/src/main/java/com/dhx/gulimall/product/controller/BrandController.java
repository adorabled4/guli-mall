package com.dhx.gulimall.product.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.dhx.gulimall.common.valid.AddGroup;
import com.dhx.gulimall.common.valid.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dhx.gulimall.product.entity.BrandEntity;
import com.dhx.gulimall.product.service.BrandService;
import com.dhx.gulimall.common.utils.PageUtils;
import com.dhx.gulimall.common.utils.R;

import javax.validation.Valid;


/**
 * 品牌
 *
 * @author dhx
 * @email dhx2648466390@163.com
 * @date 2022-12-25 11:20:10
 */
@RestController
@RequestMapping("product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
    public R info(@PathVariable("brandId") Long brandId){
		BrandEntity brand = brandService.getById(brandId);

        return R.ok().put("brand", brand);
    }

    /**
     * 保存   @Valid 表示参数需要校验, 具体的格式在 属性的名称上添加注解 javax.validation.*
     */ // , BindingResult result 不再处理异常  , 直接吧异常抛出, 然后通过 javax.validation 包来获取具体柴恩异常信息(吧异常信息写到实体类中)
    @RequestMapping("/save")
    public R save(@Validated(value = AddGroup.class) @RequestBody BrandEntity brand ){
		brandService.save(brand);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@Validated(value = UpdateGroup.class)@RequestBody BrandEntity brand){
		brandService.updateById(brand);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] brandIds){
		brandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }

}
