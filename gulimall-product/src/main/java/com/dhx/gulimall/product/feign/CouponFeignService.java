package com.dhx.gulimall.product.feign;

import com.dhx.gulimall.common.to.SkuReductionTo;
import com.dhx.gulimall.common.to.SpuBoundTo;
import com.dhx.gulimall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author dhx_
 * @className SpuFeignService
 * @date : 2023/01/19/ 23:09
 **/
@FeignClient("gulimall-coupon")
public interface CouponFeignService {

    @PostMapping("/coupon/spubounds/save")
    R saveSpuBounds(@RequestBody SpuBoundTo spuBoundTo);
    @PostMapping("/coupon/skufullreduction/saveInfo")
    R saveSkuReduction(@RequestBody SkuReductionTo skuReductionTo);
}
