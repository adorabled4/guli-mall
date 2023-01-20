package com.dhx.gulimall.product.service.impl;

import com.dhx.gulimall.product.service.CategoryBrandRelationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dhx.gulimall.common.utils.PageUtils;
import com.dhx.gulimall.common.utils.Query;

import com.dhx.gulimall.product.dao.BrandDao;
import com.dhx.gulimall.product.entity.BrandEntity;
import com.dhx.gulimall.product.service.BrandService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {


    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        // 1. 获取key
        String key = (String) params.get("key");
        // key 可能是id 也有可能是某个关键词
        QueryWrapper<BrandEntity> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(key)){
            wrapper.eq("brand_id",key).or().like("name",key);
        }
        // 进行分页查询
        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params), wrapper
        );
        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void updateDetail(BrandEntity brand) {
        // 需要更新多个表的数据
        this.updateById(brand);
        if(StringUtils.isEmpty(brand.getName())){
            // 需要同步更新其他关联表里面的数据
            categoryBrandRelationService.updateBrand(brand.getBrandId(),brand.getName());
        }
    }
}