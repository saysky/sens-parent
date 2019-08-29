package com.liuyanzhao.sens.user.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuyanzhao.sens.user.api.entity.MessageSend;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 言曌
 * @date 2019-08-28 20:35
 */
@Mapper
public interface MessageSendMapper extends BaseMapper<MessageSend> {

    /**
     * 通过消息id删除
     * @param messageId
     */
    void deleteByMessageId(String messageId);
}
