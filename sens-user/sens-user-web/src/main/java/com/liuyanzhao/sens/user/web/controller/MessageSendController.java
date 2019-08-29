package com.liuyanzhao.sens.user.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuyanzhao.sens.common.dto.QueryCondition;
import com.liuyanzhao.sens.common.util.PageUtil;
import com.liuyanzhao.sens.common.vo.PageVo;
import com.liuyanzhao.sens.common.vo.Response;
import com.liuyanzhao.sens.common.vo.SearchVo;
import com.liuyanzhao.sens.user.api.entity.Message;
import com.liuyanzhao.sens.user.api.entity.MessageSend;
import com.liuyanzhao.sens.user.api.entity.User;
import com.liuyanzhao.sens.user.api.service.MessageSendService;
import com.liuyanzhao.sens.user.api.service.MessageService;
import com.liuyanzhao.sens.user.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * 消息发送管理接口
 * @author liuyanzhao
 */
@Slf4j
@RestController
@RequestMapping("/messageSend")
@Transactional
public class MessageSendController  {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageSendService messageSendService;

    /**
     * 多条件分页获取
     * @param ms
     * @param pv
     * @return
     */
    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    public Response<Page<MessageSend>> getByCondition(@ModelAttribute MessageSend ms,
                                                      @ModelAttribute PageVo pv){

        QueryCondition<MessageSend> queryCondition = new QueryCondition(ms, new SearchVo(),  PageUtil.initMpPage(pv));
        Page<MessageSend> page = messageSendService.findByCondition(queryCondition);
        // lambda
        page.getRecords().forEach(item->{
            User u = userService.findById(item.getUserId());
            item.setUsername(u.getUsername());
            Message m = messageService.findById(item.getMessageId());
            item.setTitle(m.getTitle());
            item.setContent(m.getContent());
            item.setType(m.getType());
        });
        return Response.yes(page);
    }
}
