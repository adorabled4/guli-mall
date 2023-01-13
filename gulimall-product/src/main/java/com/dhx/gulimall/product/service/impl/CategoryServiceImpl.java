package com.dhx.gulimall.product.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dhx.gulimall.common.utils.PageUtils;
import com.dhx.gulimall.common.utils.Query;

import com.dhx.gulimall.product.dao.CategoryDao;
import com.dhx.gulimall.product.entity.CategoryEntity;
import com.dhx.gulimall.product.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {


    @Override
    public void removeMenuByIds(List<Long> asList) {
        //todo 检查当前需要删除的菜单是否被别的地方引用
        baseMapper.deleteBatchIds(asList);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 我们在数据库中存储的商品的分类 是按照 级来分类的, 父分类的id为0 的分类为 一级分类
     * @return 返回一级菜单, 子菜单都包含在了格子的parent里面
     */
    @Override
    public List<CategoryEntity> listWithTree() {
        //1. 查出所有的分类
        List<CategoryEntity> entities = list();
        
        //2. 组装成父子的树形结构
        //2.1 找出所有的一级分类
        List<CategoryEntity> level1Menus = entities.stream().filter(
                categoryEntity -> categoryEntity.getParentCid() == 0)
                .map((menu)->{
                    menu.setChildren(getChildren(menu,entities)); // 查找子菜单
                    return menu;
                }).sorted((menu1,menu2)->{
                    return (menu1.getSort()==null?0:menu1.getSort()) - (menu2.getSort()==null?0:menu2.getSort());
                }).collect(Collectors.toList());

        return level1Menus;
    }

    /**
     *  查找当前菜单的所有子菜单
     * @param root 当前菜单
     * @param all 所有的菜单的集合
     * @return 子菜单
     */
    private List<CategoryEntity> getChildren(CategoryEntity root,List<CategoryEntity> all){
        List<CategoryEntity> children = all.stream().filter(category -> {
            return category.getParentCid() == root.getCatId();
        }).map((menu) -> {
            //1. 找到子菜单
            menu.setChildren(getChildren(menu, all)); // 查找子菜单
            return menu;
        }).sorted((menu1, menu2) -> {
            //2. 对子菜单进行排序
            return (menu1.getSort()==null?0:menu1.getSort()) - (menu2.getSort()==null?0:menu2.getSort());
        }).collect(Collectors.toList());
        return children;
    }
}