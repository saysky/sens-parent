package com.liuyanzhao.sens.user.api.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.liuyanzhao.sens.common.base.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 字典
 *
 * @author liuyanzhao
 */
@Data
@TableName("t_dict")
public class Dict extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字典名称
     */
    private String title;

    /**
     * 字典类型
     */
    private String type;

    /**
     * 备注
     */
    private String description;

    /**
     * 排序值
     */
    private BigDecimal sortOrder;
}