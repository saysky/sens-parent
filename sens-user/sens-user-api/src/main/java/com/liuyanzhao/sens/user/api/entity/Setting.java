package com.liuyanzhao.sens.user.api.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.liuyanzhao.sens.common.base.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 配置
 * @author liuyanzhao
 */
@Data
@TableName("t_setting")
@NoArgsConstructor
public class Setting extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 配置值value
     */
    private String value;

    public Setting(String id){
        super.setId(id);
    }
}