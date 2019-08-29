package com.liuyanzhao.sens.common.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuyanzhao.sens.common.vo.SearchVo;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询封装类
 * @author 言曌
 * @date 2019-08-16 13:45
 */
@Data
public class QueryCondition<T> implements Serializable {

    /**
     * 根据字段筛选
     */
    private T data;

    /**
     * 一般筛选
     */
    private SearchVo searchVo;

    /**
     * 分页
     */
    private Page<T> page;

    public QueryCondition() {
    }

    public QueryCondition(Page<T> page) {
        this.page = page;
    }

    public QueryCondition(SearchVo searchVo, Page<T> page) {
        this.searchVo = searchVo;
        this.page = page;
    }

    public QueryCondition(T data, SearchVo searchVo, Page<T> page) {
        this.data = data;
        this.searchVo = searchVo;
        this.page = page;
    }
}
