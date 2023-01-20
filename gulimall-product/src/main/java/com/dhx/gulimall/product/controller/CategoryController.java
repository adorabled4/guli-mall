package com.dhx.gulimall.product.controller;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.dhx.gulimall.product.entity.CategoryEntity;
import com.dhx.gulimall.product.service.CategoryService;
import com.dhx.gulimall.common.utils.PageUtils;
import com.dhx.gulimall.common.utils.R;



/**
 * 商品三级分类
 *
 * @author dhx
 * @email dhx2648466390@163.com
 * @date 2022-12-25 11:20:10
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 列表 =>  查出所有分类以及子分类，以树形结构组装起来
     */
    @RequestMapping(value = "/list/tree" ,method = RequestMethod.GET)
    public R list(){
        List<CategoryEntity> list = categoryService.listWithTree();

//        PageUtils page = categoryService.queryPage(params);

        return R.ok().put("data", list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    public R info(@PathVariable("catId") Long catId){
		CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("data", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryEntity category){
		categoryService.save(category);

        return R.ok();
    }

    @RequestMapping("/update/sort")
    public R updateSort(@RequestBody CategoryEntity[] category){
        categoryService.updateBatchById(Arrays.asList(category));
        return R.ok();
    }
    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CategoryEntity category){
		categoryService.updateCascade(category);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] catIds){
		categoryService.removeByIds(Arrays.asList(catIds));
        categoryService.removeMenuByIds(Arrays.asList(catIds));
        return R.ok();
    }
    class Solution {
        public List<List<String>> groupAnagrams(String[] strs) {
            Map<String, List<String>> map = new HashMap<>();
            for(String s:strs){
                String key=check(s);
                if(!map.containsKey(key)){
                    List<String> list=new ArrayList<>();
                    list.add(s);
                    map.put(key,list);
                }else{
                    map.get(key).add(s);
                }
            }
            return new ArrayList(map.values());
        }
        String check(String s){
            // 如果使用单纯的加法 , 那么会出问题 a+y=b+z
            char[]chars=s.toCharArray();
            char[]count=new char[26];
            int res=0;
            for(char c: chars){
                count[c-'a']++;
            }
            return new String(count);
        }
    }
}
