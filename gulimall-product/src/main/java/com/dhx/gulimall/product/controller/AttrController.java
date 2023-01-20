package com.dhx.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.dhx.gulimall.product.entity.ProductAttrValueEntity;
import com.dhx.gulimall.product.service.AttrAttrgroupRelationService;
import com.dhx.gulimall.product.service.ProductAttrValueService;
import com.dhx.gulimall.product.vo.AttrGroupRelationVo;
import com.dhx.gulimall.product.vo.AttrRespVo;
import com.dhx.gulimall.product.vo.AttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ConditionalOnEnabledResourceChain;
import org.springframework.web.bind.annotation.*;

import com.dhx.gulimall.product.entity.AttrEntity;
import com.dhx.gulimall.product.service.AttrService;
import com.dhx.gulimall.common.utils.PageUtils;
import com.dhx.gulimall.common.utils.R;

import javax.annotation.Resource;


/**
 * 商品属性
 *
 * @author dhx
 * @email dhx2648466390@163.com
 * @date 2022-12-25 11:20:10
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Resource
    private AttrService attrService;

    @Resource
    ProductAttrValueService productAttrValueService;

    @Resource
    private AttrAttrgroupRelationService attrAttrgroupRelationService;



    ///product/attr/update/{spuId)
    @PostMapping("/update/{spuId}")
    public R updateSpuAttr(@PathVariable("spuId")Long spuId,
                           List<ProductAttrValueEntity>entities){
        productAttrValueService.updateSpuAttr(spuId,entities);
        return  R.ok();
    }

    @GetMapping("/base/listforspu/{spuId}")
    public R baseAttrListForSpu(@PathVariable("spuId")Long spuId){
        List<ProductAttrValueEntity> entities= productAttrValueService.baseAttrListForSpu(spuId);
        return R.ok().put("data",entities);
    }
    @GetMapping("/base/list/{catalogId}")
    public R baseAttrList(@RequestParam Map<String, Object> params, @PathVariable("catalogId") Long catalogId,@RequestParam("attrType")String type){
        PageUtils page = attrService.queryBaseAttrPage(params,catalogId,type);
        return R.ok().put("page", page);
    }
    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("page", page);
    }



    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    public R info(@PathVariable("attrId") Long attrId){
        AttrRespVo  attrRespVo=attrService.getAttrInfo(attrId);
        return R.ok().put("attr", attrRespVo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrVo attr){
		attrService.saveAttr(attr);

        return R.ok();
    }

    /**
     * 添加属性与 分组的关联关系
     */
    @PostMapping("/attr/relation")
    public R addRelation(@RequestBody AttrGroupRelationVo[]vos){
        attrAttrgroupRelationService.saveBatch(vos);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrVo attr){
		attrService.updateAttr(attr);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrIds){
		attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
