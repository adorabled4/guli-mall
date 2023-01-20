package com.dhx.gulimall.product.service.impl;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dhx.gulimall.common.utils.PageUtils;
import com.dhx.gulimall.common.utils.Query;

import com.dhx.gulimall.product.dao.SpuImagesDao;
import com.dhx.gulimall.product.entity.SpuImagesEntity;
import com.dhx.gulimall.product.service.SpuImagesService;


@Service("spuImagesService")
public class SpuImagesServiceImpl extends ServiceImpl<SpuImagesDao, SpuImagesEntity> implements SpuImagesService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuImagesEntity> page = this.page(
                new Query<SpuImagesEntity>().getPage(params),
                new QueryWrapper<SpuImagesEntity>()
        );
        return new PageUtils(page);
    }

    @Override
    public void saveImages(List<String> images, Long spuInfoId) {
        if(images==null || images.size()==0){
            return ;
        }
        List<SpuImagesEntity> collect = images.stream().map(image -> {
            SpuImagesEntity spuImagesEntity = new SpuImagesEntity();
            spuImagesEntity.setSpuId(spuInfoId);
            spuImagesEntity.setImgUrl(image);
            return spuImagesEntity;
        }).collect(Collectors.toList());
        this.saveBatch(collect);
    }
}