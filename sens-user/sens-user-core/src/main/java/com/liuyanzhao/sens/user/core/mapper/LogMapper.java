package com.liuyanzhao.sens.user.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuyanzhao.sens.user.api.entity.Log;
import com.liuyanzhao.sens.user.api.entity.Message;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author 言曌
 * @date 2019-08-09 15:15
 */
@Mapper
public interface LogMapper extends BaseMapper<Log> {

    /**
     * 清空所有
     * @return
     */
    Integer deleteAll();
}
