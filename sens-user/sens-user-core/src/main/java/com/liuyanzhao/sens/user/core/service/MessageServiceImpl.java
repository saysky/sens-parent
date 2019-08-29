package com.liuyanzhao.sens.user.core.service;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuyanzhao.sens.common.dto.QueryCondition;
import com.liuyanzhao.sens.common.vo.Response;
import com.liuyanzhao.sens.common.vo.SearchVo;
import com.liuyanzhao.sens.user.api.entity.Message;
import com.liuyanzhao.sens.user.api.service.MessageService;
import com.liuyanzhao.sens.user.core.mapper.MessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;


/**
 * @author 言曌
 * @date 2019-08-07 08:45
 */
@RestController
@Slf4j
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;


    @Override
    public Message findById(String id) {
        return messageMapper.selectById(id);
    }

    @Override
    public Integer getTotalCount() {
        return messageMapper.selectCount(null);
    }

    @Override
    public Message insert(Message entity) {
        messageMapper.insert(entity);
        return entity;
    }

    @Override
    public Message update(Message entity) {
        messageMapper.updateById(entity);
        return entity;
    }

    @Override
    public void deleteById(String id) {
        messageMapper.deleteById(id);
    }

    @Override
    public void deleteBatchIds(List<String> ids) {
        messageMapper.deleteBatchIds(ids);
    }

    @Override
    public Page<Message> findByCondition(QueryCondition<Message> messageCondition) {
        Message message = messageCondition.getData();
        Page page = messageCondition.getPage();
        SearchVo searchVo = messageCondition.getSearchVo();

        //对指定字段查询
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        if (message != null) {
            if (message.getTitle() != null) {
                queryWrapper.like("title", message.getTitle());
            }
            if (message.getContent() != null) {
                queryWrapper.like("content", message.getContent());
            }
            if (message.getType() != null) {
                queryWrapper.eq("type", message.getType());
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
        Page<Message> messagePage = (Page<Message>) messageMapper.selectPage(page, queryWrapper);
        return messagePage;
    }

    @Override
    public List<Message> findByCreateSend(Boolean createSend) {
        List<Message> messages = messageMapper.selectList(
                new QueryWrapper<Message>().eq("create_send", createSend)
        );
        return messages;
    }
}
