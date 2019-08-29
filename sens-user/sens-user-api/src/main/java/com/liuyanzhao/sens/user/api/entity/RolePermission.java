package com.liuyanzhao.sens.user.api.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.liuyanzhao.sens.common.base.BaseEntity;
import lombok.Data;

/**
 * 角色权限
 * @author 言曌
 * @date 2019-08-10 15:28
 */

@Data
@TableName("t_role_permission")
public class RolePermission extends BaseEntity {

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 权限id
     */
    private String permissionId;
}
