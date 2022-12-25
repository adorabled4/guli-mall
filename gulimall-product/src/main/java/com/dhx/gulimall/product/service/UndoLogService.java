package com.dhx.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dhx.gulimall.common.utils.PageUtils;
import com.dhx.gulimall.product.entity.UndoLogEntity;

import java.util.Map;

/**
 * 
 *
 * @author dhx
 * @email dhx2648466390@163.com
 * @date 2022-12-25 11:20:10
 */
public interface UndoLogService extends IService<UndoLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

