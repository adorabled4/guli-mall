package com.dhx.gulimall.ware.vo;

import lombok.Data;

import java.util.List;

/**
 * @author dhx_
 * @className MergeVo
 * @date : 2023/01/20/ 21:28
 **/
@Data
public class MergeVo {
    private Long purchaseId;//整单id
    private List<Long> items;//[1,2,3,4]//合并项集合
}
