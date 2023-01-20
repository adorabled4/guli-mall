package com.dhx.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dhx.gulimall.product.entity.BrandEntity;
import com.dhx.gulimall.product.vo.BrandVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.dhx.gulimall.product.entity.CategoryBrandRelationEntity;
import com.dhx.gulimall.product.service.CategoryBrandRelationService;
import com.dhx.gulimall.common.utils.PageUtils;
import com.dhx.gulimall.common.utils.R;



/**
 * 品牌分类关联
 *
 * @author dhx
 * @email dhx2648466390@163.com
 * @date 2022-12-25 11:20:10
 */
@RestController
@RequestMapping("product/categorybrandrelation")
public class CategoryBrandRelationController {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;


    /**
     *  获取分类关联的品牌
     *  controller: 处理请求, 接收和校验数据
     *  service来接收controller传来的数据进行业务处理
     *  最后controller接收service 处理完的数据 , 封装页面指定的vo
     */
    @GetMapping("/brand/list")
    public R relationBrandList(@RequestParam(value="catId" ,required=true)Long catId){
        List<BrandEntity> brands=  categoryBrandRelationService.getBrandsByCatId(catId);
        List<BrandVo> vos = brands.stream().map(brand -> {
            BrandVo brandVo = new BrandVo();
            brandVo.setBrandName(brand.getName());
            brandVo.setBrandId(brand.getBrandId());
            return brandVo;
        }).collect(Collectors.toList());
        return R.ok().put("data",vos);
    }
    /**
     *  获取当前品牌关联的所有分类例表
     */
    @GetMapping(value = "/catelog/list")
    public R catelogList(@RequestParam Long brandId){
        List<CategoryBrandRelationEntity> data = categoryBrandRelationService.list(
                new QueryWrapper<CategoryBrandRelationEntity>().eq("brand_id", brandId));

        return R.ok().put("page", data);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = categoryBrandRelationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		CategoryBrandRelationEntity categoryBrandRelation = categoryBrandRelationService.getById(id);

        return R.ok().put("categoryBrandRelation", categoryBrandRelation);
    }

    /**
     * 保存 => 需要保存详细信息
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
		categoryBrandRelationService.saveDetail(categoryBrandRelation);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
		categoryBrandRelationService.updateById(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		categoryBrandRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
