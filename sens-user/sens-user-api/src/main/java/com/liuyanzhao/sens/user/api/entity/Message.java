package com.liuyanzhao.sens.user.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.liuyanzhao.sens.common.base.BaseEntity;
import lombok.Data;


/**
 * 消息
 *
 * @author liuyanzhao
 */
@Data
@TableName("t_message")
public class Message extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 消息类型
     */
    private String type;

    /**
     * 新创建账号也推送
     */
    private Boolean createSend;

    /**
     * 发送范围
     */
    @TableField(exist = false)
    private Integer range;

    /**
     * 发送指定用户id
     */
    @TableField(exist = false)
    private String[] userIds;
}