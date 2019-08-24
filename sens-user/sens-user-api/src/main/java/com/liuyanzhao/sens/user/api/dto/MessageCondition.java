package com.liuyanzhao.sens.user.api.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuyanzhao.sens.common.vo.SearchVo;
import com.liuyanzhao.sens.user.api.entity.User;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 言曌
 * @date 2019-08-12 13:22
 */
@Data
public class MessageCondition implements Serializable {

    /**
     * 用户
     */
    private User user;

    /**
     * 搜索
     */
    private SearchVo searchVo;


    /**
     * 分页
     */
    private Page<User> page;

    public MessageCondition() {
    }

    public MessageCondition(User user, SearchVo searchVo, Page<User> page) {
        this.user = user;
        this.searchVo = searchVo;
        this.page = page;
    }
}
