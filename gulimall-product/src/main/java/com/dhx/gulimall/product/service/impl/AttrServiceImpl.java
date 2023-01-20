package com.dhx.gulimall.product.service.impl;

import com.dhx.gulimall.common.constant.ProductConstant;
import com.dhx.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.dhx.gulimall.product.dao.AttrGroupDao;
import com.dhx.gulimall.product.dao.CategoryDao;
import com.dhx.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.dhx.gulimall.product.entity.AttrGroupEntity;
import com.dhx.gulimall.product.entity.CategoryEntity;
import com.dhx.gulimall.product.service.CategoryService;
import com.dhx.gulimall.product.vo.AttrGroupRelationVo;
import com.dhx.gulimall.product.vo.AttrRespVo;
import com.dhx.gulimall.product.vo.AttrVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dhx.gulimall.common.utils.PageUtils;
import com.dhx.gulimall.common.utils.Query;

import com.dhx.gulimall.product.dao.AttrDao;
import com.dhx.gulimall.product.entity.AttrEntity;
import com.dhx.gulimall.product.service.AttrService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Resource
    AttrAttrgroupRelationDao relationDao;

    @Resource
    AttrGroupDao attrGroupDao;

    @Resource
    CategoryDao categoryDao;

    @Resource
    CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void saveAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        this.save(attrEntity);
        BeanUtils.copyProperties(attr,AttrEntity.class);
        //保存基本数据
        this.save(attrEntity);
        // 保存关联关系 => 我们只需要对基本类型今天添加, 比如添加`颜色`类型, 而不是去添加 黑色, 白色,
        if(attr.getAttrType()== ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() && attr.getAttrGroupId()!=null){
            // 只把基本属性添加到关联表中
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrId(attr.getAttrGroupId());
            relationEntity.setAttrId(attrEntity.getAttrId());
            relationDao.insert(relationEntity);
        }
    }


    /**
     * 分页查询 基本属性
     * @param params
     * @param catelogId
     * @param type
     * @return
     */
    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId,String type) {
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<>();
        if(catelogId !=0 ){
            wrapper.eq("catelog_id",catelogId)
                    .eq("attr_type","base".equalsIgnoreCase(type)?ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode():ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());
        }
        // 如果有条件 , 进行条件查询
        String key = (String) params.get("key");
        if(StringUtils.isNotEmpty(key)){
            wrapper.and((queryWrapper)->{
                queryWrapper.eq("attr_id",key).or().like("attr_name",key);
            });
        }
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                wrapper
        );
        List<AttrEntity> records = page.getRecords();
        List<AttrRespVo> attrRespVos = records.stream().map(attrEntity -> {
            AttrRespVo attrRespVo = new AttrRespVo();
            BeanUtils.copyProperties(attrEntity, attrRespVo);
            //1. 设置分类以及分组的名称 => 通过中间表获得相应的 数据的id , 然后去别的表中查询
            if("base".equalsIgnoreCase(type)){
                AttrAttrgroupRelationEntity attr_id = relationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId()));
                if(attr_id!=null && attr_id.getAttrGroupId()!=null){
                    AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attr_id.getAttrGroupId());
                    attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }
            }
            CategoryEntity category = categoryDao.selectById(attrEntity.getCatelogId());
            if(category!=null){
                attrRespVo.setCatelogName(category.getName());
            }
            return attrRespVo;
        }).collect(Collectors.toList());
        PageUtils pageUtils = new PageUtils(page);
        pageUtils.setList(attrRespVos);
        return  pageUtils;
    }

    @Override
        public AttrRespVo getAttrInfo(Long attrId) {
        AttrEntity attrEntity = this.getById(attrId);
        AttrRespVo attrRespVo = new AttrRespVo();
        BeanUtils.copyProperties(attrEntity, attrRespVo);
        // 只有基本类型才需要在关联表中写入关联信息
        if(attrEntity.getAttrType()==ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){
            //1. 设置分组信息
            AttrAttrgroupRelationEntity relationEntity = relationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId()));
            if(relationEntity!=null){
                AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(relationEntity.getAttrGroupId());
                if(attrGroupEntity!=null){
                    attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
                    attrRespVo.setAttrGroupId(attrGroupEntity.getAttrGroupId());
                }
            }
        }
        //2. 设置分类信息
        Long catelogId = attrEntity.getCatelogId();
        CategoryEntity category = categoryDao.selectById(catelogId);
        Long[] path = categoryService.findCatelogPath(catelogId);
        attrRespVo.setCatelogPath(path);
        if(category!=null){
            attrRespVo.setCatelogName(category.getName());
        }
        return attrRespVo;
    }

    @Transactional
    @Override
    public void updateAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr,attrEntity);
        updateById(attrEntity);
        if(attrEntity.getAttrType()==ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){
            //1. 修改分组关联
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrGroupId(attr.getAttrGroupId());
            relationEntity.setAttrId(attr.getAttrId());
            Long count = relationDao.selectCount(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attr.getAttrId()));
            // 这里是通过判断是否已经在表中存在来选择是 更新还是 插入  (都是操作关联表, 因此需要判断是否是基本属性)
            if(count>0){
                relationDao.update(relationEntity,new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id",attr.getAttrId()));
            }else{
                relationDao.insert(relationEntity);
            }
        }
    }

    /**
     * 根据分组id 查找出关联的所有属性
     * @param attrgroupId
     * @return
     */
    @Override
    public List<AttrEntity> getRelationAttr(Long attrgroupId) {
        List<AttrAttrgroupRelationEntity> relationEntities = relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id",attrgroupId));
        List<Long> attrIds = relationEntities.stream().map(relation -> {
            return relation.getAttrId();
        }).collect(Collectors.toList());
        if(attrIds==null || attrIds.size()==0){
            return null;
        }
        List<AttrEntity> attrEntities = this.listByIds(attrIds);
        return attrEntities;
    }

    @Override
    public void deleteRelation(AttrGroupRelationVo[] vos) {
        for (AttrGroupRelationVo vo : vos) {
            relationDao.delete(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id",vo.getAttrId()).eq("attr_group_id",vo.getAttrGroupId()));
        }
        List<AttrAttrgroupRelationEntity> relations = Arrays.asList(vos).stream().map(vo -> {
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(vo, relationEntity);
            return relationEntity;
        }).collect(Collectors.toList());
        relationDao.deleteBatchRelation(relations);
        // 逐个删除比较耗费性能, 我们通过批量删除来实现
        // DELETE FROM pms attr attrgroup relation WHERE (attr id=1 AND attr group id=1) OR (attr id=3 AND attr group id=2)
    }

    /**
     * 获取没有被当前分组关联的所有属性
     * @param attrgroupId
     * @param params
     * @return
     */
    @Override
    public PageUtils getNoRelationAttr(Long attrgroupId, Map<String, Object> params) {
        //1. 当前分组只能关联自己所属的分类里面的属性
        AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrgroupId);
        Long catelogId = attrGroupEntity.getCatelogId();
        //2. 当前分组只能关联别的分组没有引用的属性
        //2.1 当前分类下的其他分组
        List<AttrGroupEntity> groups = attrGroupDao.selectList(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));
        // 获取所有分组的id
        List<Long> groupsIds = groups.stream().map(AttrGroupEntity::getAttrGroupId).collect(Collectors.toList());
        //2.2 获取这些分组关联的属性的id
        List<AttrAttrgroupRelationEntity> relationEntities = relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id", groupsIds));
        List<Long> attrIds = relationEntities.stream().map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());
        //2.3 从当前分类中所以的属性中移除已经关联的属性   attrIds 对应其他的分组关联的 属性 , attrGroupId用来排除当前分组关联的属性
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>().eq("catelog_id",catelogId).eq("attr_type",ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode());
        if(attrIds!=null && attrIds.size()!=0){
            queryWrapper.notIn("attr_id", attrIds);
        }
        //判断是否有参数进行模糊查询
        String key = (String) params.get("key");
        if(StringUtils.isNotEmpty(key)){
            queryWrapper.and((wrapper)->{
                wrapper.eq("attr_id",key).or().like("attr_name",key);
            });
        }
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), queryWrapper);
        return new PageUtils(page);
    }
}