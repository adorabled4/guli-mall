package com.dhx.gulimall.ware.feign;

import com.dhx.gulimall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author dhx_
 * @className ProductFeignService
 * @date : 2023/01/20/ 22:47
 **/
@FeignClient("gulimall-product")
public interface ProductFeignService {


    @RequestMapping("/product/skuinfo/info/{skuId]")
    R info(@PathVariable("skuId")Long skuId);
}
