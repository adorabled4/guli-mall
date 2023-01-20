package com.dhx.gulimall.product.service.impl;

import com.dhx.gulimall.product.entity.AttrEntity;
import com.dhx.gulimall.product.service.AttrService;
import com.dhx.gulimall.product.vo.AttrGroupWithAttrsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dhx.gulimall.common.utils.PageUtils;
import com.dhx.gulimall.common.utils.Query;

import com.dhx.gulimall.product.dao.AttrGroupDao;
import com.dhx.gulimall.product.entity.AttrGroupEntity;
import com.dhx.gulimall.product.service.AttrGroupService;

import javax.annotation.Resource;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Resource
    AttrService attrService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    // 获取 通过三级分类查询属性分组 SPU
    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catelogId) {
        if(catelogId==0){
            IPage<AttrGroupEntity> page = this.page(
                    new Query<AttrGroupEntity>().getPage(params),
                    new QueryWrapper<AttrGroupEntity>() // 这个是查询条件
            );
            return new PageUtils(page);
        }else{
            String key = (String)params.get("key");
            QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<AttrGroupEntity>().eq("catelog_id",catelogId);
            if(StringUtils.isNotEmpty(key)){
                wrapper.and(obj->{ // 这里的and 相当于加一个 ( )
                    obj.eq("attr_group_id",key).or().like("attr_group_name",key);
                });
            } // 这个地方由于不知道前端传入的key 是查询id 还是名字, 因此通过or对两个字段都进行查询
            //SELECT * from pms_attr_group where catelog_id = ? ( and attr_group_id = key or attr_group_name like %key% )
            IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params), wrapper);
            return new PageUtils(page);
        }
    }

    /**
     * 根据分类i查出所有的分组以及这些组里面的属性
     * @param catelogId
     * @return
     */
    @Override
    public List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatalogId(Long catelogId) {
        //1. 查询分组信息
        List<AttrGroupEntity> groupEntities = this.list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));
        //2. 查询所有属性
        List<AttrGroupWithAttrsVo> collect = groupEntities.stream().map(attrGroupEntity -> {
            AttrGroupWithAttrsVo attrGroupWithAttrsVo = new AttrGroupWithAttrsVo();
            BeanUtils.copyProperties(attrGroupEntity,attrGroupWithAttrsVo);
            List<AttrEntity> attrs = attrService.getRelationAttr(attrGroupEntity.getAttrGroupId());
            attrGroupWithAttrsVo.setAttrs(attrs);
            return attrGroupWithAttrsVo;
        }).collect(Collectors.toList());
        return collect;
    }
}