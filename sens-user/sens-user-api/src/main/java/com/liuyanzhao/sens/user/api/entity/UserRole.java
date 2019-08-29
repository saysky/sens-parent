package com.liuyanzhao.sens.user.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.liuyanzhao.sens.common.base.BaseEntity;
import lombok.Data;

/**
 * 用户角色
 *
 * @author 言曌
 * @date 2019-08-10 15:29
 */
@Data
@TableName("t_user_role")
public class UserRole extends BaseEntity {

    /**
     * 用户唯一id
     */
    private String userId;

    /**
     * 角色唯一id
     */
    private String roleId;

    /**
     * 角色名
     */
    @TableField(exist = false)
    private String roleName;
}
