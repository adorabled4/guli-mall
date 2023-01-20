package com.dhx.gulimall.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author dhx_
 * @className SpuBoundTo
 * @date : 2023/01/19/ 23:17
 **/
@Data
public class SpuBoundTo {
    private Long spuId;
    private BigDecimal buyBounds;
    private BigDecimal growBounds;
}
