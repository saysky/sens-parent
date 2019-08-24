package com.liuyanzhao.sens.user.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.liuyanzhao.sens.common.base.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * 用户
 *
 * @author 言曌
 * @date 2019-08-07 00:37
 */
@Data
@TableName("t_user")
public class User extends BaseEntity {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 显示名
     */
    private String nickname;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别: 男1/女0
     */
    private String sex;

    /**
     * 用户头像，默认=CommonConstant.USER_DEFAULT_AVATAR
     */
    private String avatar;

    /**
     * 用户类型：普通用户0/管理员用户1。默认普通用户0=CommonConstant.USER_TYPE_NORMAL
     */
    private Integer type;

    /**
     * 状态 正常0/拉黑-1。 默认正常0=CommonConstant.USER_STATUS_NORMAL
     */
    private Integer status;

    /**
     * 描述
     */
    private String description;

    /**
     * 拥有的角色
     */
    @TableField(exist = false)
    private List<Role> roles;

    /**
     * 拥有的权限
     */
    @TableField(exist = false)
    private List<Permission> permissions;
}
