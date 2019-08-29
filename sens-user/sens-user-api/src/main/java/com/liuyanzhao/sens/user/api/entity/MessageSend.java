package com.liuyanzhao.sens.user.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.liuyanzhao.sens.common.base.BaseEntity;
import com.liuyanzhao.sens.common.constant.CommonConstant;
import lombok.Data;

/**
 * 消息发送详情
 *
 * @author liuyanzhao
 */
@Data
@TableName("t_message_send")
public class MessageSend extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 关联消息id
     */
    private String messageId;

    /**
     * 关联用户id
     */
    private String userId;

    /**
     * 状态 0默认未读 1已读 2回收站
     */
    private Integer status = CommonConstant.MESSAGE_STATUS_UNREAD;

    /**
     * 发送用户名
     */
    @TableField(exist = false)
    private String username;

    /**
     * 消息标题
     */
    @TableField(exist = false)
    private String title;

    /**
     * 消息内容
     */
    @TableField(exist = false)
    private String content;

    /**
     * 消息类型
     */
    @TableField(exist = false)
    private String type;
}