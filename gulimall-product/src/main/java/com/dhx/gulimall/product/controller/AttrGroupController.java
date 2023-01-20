package com.dhx.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.dhx.gulimall.product.entity.AttrEntity;
import com.dhx.gulimall.product.service.AttrService;
import com.dhx.gulimall.product.service.CategoryService;
import com.dhx.gulimall.product.vo.AttrGroupRelationVo;
import com.dhx.gulimall.product.vo.AttrGroupWithAttrsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.dhx.gulimall.product.entity.AttrGroupEntity;
import com.dhx.gulimall.product.service.AttrGroupService;
import com.dhx.gulimall.common.utils.PageUtils;
import com.dhx.gulimall.common.utils.R;

import javax.annotation.Resource;


/**
 * 属性分组
 *
 * @author dhx
 * @email dhx2648466390@163.com
 * @date 2022-12-25 11:20:10
 */
@Slf4j
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    @Resource
    CategoryService categoryService;
    @Resource
    AttrService attrService;


    /**
     * 获取当前分组关联的所有属性
     * @param attrgroupId
     * @return
     */
    @GetMapping("/{attrgroupId}/attr/relation")
    public R attrRelation(@PathVariable("attrgroupId")Long attrgroupId){

        List<AttrEntity> attrEntities= attrService.getRelationAttr(attrgroupId);
        return  R.ok().put("data",attrEntities);
    }


    /**
     * 通过分配id  查询出 属性
     */
    @GetMapping("/{catalogId}/withattr")
    public R getAttrGroupWithAttrs(@PathVariable("catalogId") Long catalogId){
        List<AttrGroupWithAttrsVo> vos= attrGroupService.getAttrGroupWithAttrsByCatalogId(catalogId);
        return R.ok().put("data",vos);
    }
    /**
     * 获取没有与当前分组id 关联的 属性
     * @param attrgroupId
     * @param params
     * @return
     */
    @GetMapping("/{attrgroupId}/noattr/relation")
    public R attrNoRelation(@PathVariable("attrgroupId")Long attrgroupId,
                            @RequestParam Map<String, Object> params){
        PageUtils attrEntities= attrService.getNoRelationAttr(attrgroupId,params);
        return  R.ok().put("data",attrEntities);
    }

    /**
     * 批量删除 属性与分组的关联关系
     * @param vos
     * @return
     */
    @PostMapping("/attr/relation/delete")
    public R deleteRelation(@RequestBody AttrGroupRelationVo[]vos){
        attrService.deleteRelation(vos);
        return R.ok();
    }

    /**
     * 列表
     */
    @RequestMapping("/list/{catelogId}")
    public R list(@RequestParam Map<String, Object> params,
                  @PathVariable("catelogId")Long catelogId){
//        PageUtils page = attrGroupService.queryPage(params);
        log.debug("params ,{}",params);
        log.debug("catelogId ,{}",catelogId);
        PageUtils page = attrGroupService.queryPage(params,catelogId);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    public R info(@PathVariable("attrGroupId") Long attrGroupId){
		AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        Long catalogId = attrGroup.getCatelogId();
        Long[] path= categoryService.findCatelogPath(catalogId);


        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrGroupIds){
		attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

}
