package com.dhx.gulimall.ware.vo;

import lombok.Data;

/**
 * @author dhx_
 * @className itemVo
 * @date : 2023/01/20/ 22:17
 **/
@Data
public class PurchaseItemDoneVo {
    private Long itemId;

    private Integer status;

    private  String reason;
}
