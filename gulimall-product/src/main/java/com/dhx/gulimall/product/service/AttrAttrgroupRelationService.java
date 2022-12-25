package com.dhx.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dhx.gulimall.common.utils.PageUtils;
import com.dhx.gulimall.product.entity.AttrAttrgroupRelationEntity;

import java.util.Map;

/**
 * 属性&属性分组关联
 *
 * @author dhx
 * @email dhx2648466390@163.com
 * @date 2022-12-25 11:20:10
 */
public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

