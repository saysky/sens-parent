package com.liuyanzhao.sens.user.api.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuyanzhao.sens.common.dto.QueryCondition;
import com.liuyanzhao.sens.user.api.entity.MessageSend;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 日志接口
 *
 * @author 言曌
 * @date 2019-08-19 18:07
 */
@FeignClient(name = "sens-user-core")
public interface MessageSendService {

    /**
     * 根据ID获取
     *
     * @param id
     * @return
     */
    @GetMapping("/messageSend/findById")
    MessageSend findById(@RequestParam("id") String id);

    /**
     * 获取总数
     *
     * @return
     */
    @GetMapping("/messageSend/getTotalCount")
    Integer getTotalCount();

    /**
     * 添加
     *
     * @param entity
     * @return
     */
    @PostMapping("/messageSend/insert")
    MessageSend insert(@RequestBody MessageSend entity);

    /**
     * 修改
     *
     * @param entity
     * @return
     */
    @PutMapping("/messageSend/update")
    MessageSend update(@RequestBody MessageSend entity);


    /**
     * 根据ID删除
     *
     * @param id
     */
    @DeleteMapping("/messageSend/deleteById")
    Integer deleteById(@RequestParam("id") String id);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @DeleteMapping("/messageSend/deleteBatchIds")
    Integer deleteBatchIds(@RequestParam("ids") List<String> ids);


    /**
     * 发送消息 带websocket推送
     *
     * @param messageSend
     * @return
     */
    @PostMapping("/messageSend/send")
    MessageSend send(@RequestBody MessageSend messageSend);

    /**
     * 通过消息id删除
     * @param messageId
     */
    @DeleteMapping("/messageSend/deleteByMessageId")
    void deleteByMessageId(@RequestParam("messageId") String messageId);

    /**
     * 多条件分页获取
     * @param queryCondition
     * @return
     */
    @PostMapping("/messageSend/findByCondition")
    Page<MessageSend> findByCondition(@RequestBody QueryCondition<MessageSend> queryCondition);
}