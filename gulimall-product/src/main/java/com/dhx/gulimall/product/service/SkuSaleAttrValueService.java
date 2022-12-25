package com.dhx.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dhx.gulimall.common.utils.PageUtils;
import com.dhx.gulimall.product.entity.SkuSaleAttrValueEntity;

import java.util.Map;

/**
 * sku销售属性&值
 *
 * @author dhx
 * @email dhx2648466390@163.com
 * @date 2022-12-25 11:20:10
 */
public interface SkuSaleAttrValueService extends IService<SkuSaleAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

