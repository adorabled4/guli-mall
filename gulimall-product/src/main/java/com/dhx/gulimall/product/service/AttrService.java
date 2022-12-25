package com.dhx.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dhx.gulimall.common.utils.PageUtils;
import com.dhx.gulimall.product.entity.AttrEntity;

import java.util.Map;

/**
 * 商品属性
 *
 * @author dhx
 * @email dhx2648466390@163.com
 * @date 2022-12-25 11:20:10
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

