package com.dhx.gulimall.product.vo;

import lombok.Data;

/**
 * @author dhx_
 * @className AttrRespVo
 * @date : 2023/01/18/ 20:01
 **/
@Data
public class AttrRespVo extends AttrVo{

    /**
     * 当前属性分类的名字
     */
    private String catelogName;
    /**
     * 属性的分组的名称
     */
    private String groupName;

    /**
     * 属性的分类的路径
     */
    private Long[] catelogPath;
}
