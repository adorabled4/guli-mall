package com.dhx.gulimall.product.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.dhx.gulimall.product.entity.AttrEntity;
import lombok.Data;

import java.util.List;

/**
 * @author dhx_
 * @className AttrGroupWithAttrsVo
 * @date : 2023/01/19/ 20:06
 **/
@Data
public class AttrGroupWithAttrsVo {
    private static final long serialVersionUID = 1L;
    /**
     * 分组id
     */
    @TableId
    private Long attrGroupId;
    /**
     * 组名
     */
    private String attrGroupName;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 描述
     */
    private String descript;
    /**
     * 组图标
     */
    private String icon;
    /**
     * 所属分类id
     */
    private Long catelogId;

    private List<AttrEntity> attrs;
}
