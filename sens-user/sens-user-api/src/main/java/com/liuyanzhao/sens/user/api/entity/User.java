package com.liuyanzhao.sens.user.api.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.liuyanzhao.sens.common.constant.CommonConstant;
import com.liuyanzhao.sens.common.entity.BaseEntity;
import lombok.Data;

/**
 * 用户
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
     * 用户头像
     */
    private String avatar = CommonConstant.USER_DEFAULT_AVATAR;

    /**
     * 用户类型：普通用户0/管理员用户1。默认普通用户
     */
    private Integer type = CommonConstant.USER_TYPE_NORMAL;

    /**
     * 状态 默认0正常 -1拉黑")
     */
    private Integer status = CommonConstant.USER_STATUS_NORMAL;

    /**
     * 描述
     */
    private String description;
}
