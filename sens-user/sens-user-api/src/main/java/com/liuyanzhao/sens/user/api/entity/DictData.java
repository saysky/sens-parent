package com.liuyanzhao.sens.user.api.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.liuyanzhao.sens.common.base.BaseEntity;
import com.liuyanzhao.sens.common.constant.CommonConstant;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 字典数据
 * @author liuyanzhao
 */
@Data
@TableName("t_dict_data")
public class DictData extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 数据名称
     */
    private String title;

    /**
     * 数据值
     */
    private String value;

    /**
     * 排序值
     */
    private BigDecimal sortOrder;

    /**
     * 是否启用 0启用 -1禁用
     */
    private Integer status = CommonConstant.STATUS_NORMAL;

    /**
     * 备注
     */
    private String description;

    /**
     * 所属字典
     */
    private String dictId;
}