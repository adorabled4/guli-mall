package com.dhx.gulimall.gulimallproduct;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dhx.gulimall.product.GulimallProductApplication;
import com.dhx.gulimall.product.entity.BrandEntity;
import com.dhx.gulimall.product.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest(classes = GulimallProductApplication.class)
class GulimallProductApplicationTests {

    @Test
    void contextLoads() {
    }
    @Resource
    BrandService brandService;

    @Test
    public void insertTest(){
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setName("华为");
        brandService.save(brandEntity);
        //13
        System.out.println(brandEntity.getBrandId());
    }
    @Test
    public void updateTest(){
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setDescript("华为品牌");
        brandEntity.setBrandId(13L);
        brandService.updateById(brandEntity);
    }
    @Test
    public void selectTest(){
        List<BrandEntity> list = brandService.list(new QueryWrapper<BrandEntity>().eq("name", "华为"));
        for (BrandEntity brandEntity : list) {
            System.out.println(brandEntity.getBrandId());
        }
    }
}
