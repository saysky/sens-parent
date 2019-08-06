package com.liuyanzhao.sens.common.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 言曌
 * @date 2019-08-07 00:28
 */
@Data
public class BaseEntity implements Serializable {

    /**
     * ID，自增
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 创建人用户名
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;
}
