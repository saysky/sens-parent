package com.liuyanzhao.sens.user.core.service;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuyanzhao.sens.common.dto.QueryCondition;
import com.liuyanzhao.sens.common.vo.SearchVo;
import com.liuyanzhao.sens.user.api.entity.MessageSend;
import com.liuyanzhao.sens.user.api.service.MessageSendService;
import com.liuyanzhao.sens.user.core.mapper.MessageSendMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;


/**
 * 消息发送接口实现
 * @author liuyanzhao
 */
@Slf4j
@RestController
@Transactional
public class MessageSendServiceImpl implements MessageSendService {

    @Autowired
    private MessageSendMapper messageSendMapper;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @Override
    public MessageSend findById(String id) {
        return messageSendMapper.selectById(id);
    }

    @Override
    public Integer getTotalCount() {
        return messageSendMapper.selectCount(null);
    }

    @Override
    public MessageSend insert(MessageSend messageSend) {
        messageSendMapper.insert(messageSend);
        return messageSend;
    }

    @Override
    public MessageSend update(MessageSend entity) {
        messageSendMapper.updateById(entity);
        return entity;
    }

    @Override
    public Integer deleteById(String id) {
        return messageSendMapper.deleteById(id);
    }

    @Override
    public Integer deleteBatchIds(List<String> ids) {
        return messageSendMapper.deleteBatchIds(ids);
    }


    @Override
    public MessageSend send(MessageSend messageSend) {
        messageSendMapper.insert(messageSend);
        messagingTemplate.convertAndSendToUser(messageSend.getUserId(),"/queue/subscribe", "您收到了新的消息");
        return messageSend;
    }

    @Override
    public void deleteByMessageId(String messageId) {
        messageSendMapper.deleteByMessageId(messageId);
    }

    @Override
    public Page<MessageSend> findByCondition(QueryCondition<MessageSend> queryCondition) {

        MessageSend messageSend = queryCondition.getData();
        Page page = queryCondition.getPage();
        SearchVo searchVo = queryCondition.getSearchVo();

        //对指定字段查询
        QueryWrapper<MessageSend> queryWrapper = new QueryWrapper<>();
        if (messageSend != null) {
            if (messageSend.getMessageId() != null) {
                queryWrapper.eq("message_id", messageSend.getMessageId());
            }
        }
        if (messageSend != null) {
            if (messageSend.getUserId() != null) {
                queryWrapper.eq("user_id", messageSend.getUserId());
            }
        }
        if (messageSend != null) {
            if (messageSend.getStatus() != null) {
                queryWrapper.eq("status", messageSend.getStatus());
            }
        }
        
        //查询日期范围
        if (searchVo != null) {
            String startDate = searchVo.getStartDate();
            String endDate = searchVo.getEndDate();
            if (startDate != null && endDate != null) {
                Date start = DateUtil.parse(startDate);
                Date end = DateUtil.parse(endDate);
                queryWrapper.between("create_time", start, end);
            }
        }
        Page<MessageSend> messageSendPage = (Page<MessageSend>) messageSendMapper.selectPage(page, queryWrapper);
        return messageSendPage;
    }

}