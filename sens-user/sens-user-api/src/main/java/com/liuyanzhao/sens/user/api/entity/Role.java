package com.liuyanzhao.sens.user.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.liuyanzhao.sens.common.constant.CommonConstant;
import com.liuyanzhao.sens.common.base.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * 角色
 *
 * @author 言曌
 * @date 2019-08-10 15:27
 */
@Data
@TableName("t_role")
public class Role extends BaseEntity {

    /**
     * 角色名 以ROLE_开头
     */
    private String name;

    /**
     * 是否为注册默认角色
     */
    private Boolean defaultRole;

    /**
     * 数据权限类型 0全部默认 1自定义
     */
    private Integer dataType = CommonConstant.DATA_TYPE_ALL;

    /**
     * 备注
     */
    private String description;

    /**
     * 拥有权限
     */
    @TableField(exist = false)
    private List<RolePermission> permissions;

}
