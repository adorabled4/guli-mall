package com.dhx.gulimall.ware.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author dhx_
 * @className PurchaseDoneVo
 * @date : 2023/01/20/ 22:17
 **/
@Data
public class PurchaseDoneVo {
    @NotNull(message = "采购单id不能为空!")
    private Long id;

    private List<PurchaseItemDoneVo> items;

}
