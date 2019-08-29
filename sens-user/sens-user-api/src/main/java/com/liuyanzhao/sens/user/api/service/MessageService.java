package com.liuyanzhao.sens.user.api.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuyanzhao.sens.common.dto.QueryCondition;
import com.liuyanzhao.sens.user.api.entity.Message;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 消息接口
 *
 * @author 言曌
 * @date 2019-08-12 12:53
 */
@FeignClient(name = "sens-user-core")
public interface MessageService {

    /**
     * 根据ID获取
     *
     * @param id
     * @return
     */
    @GetMapping("/message/findById")
    Message findById(@RequestParam("id") String id);

    /**
     * 获取总数
     *
     * @return
     */
    @GetMapping("/message/getTotalCount")
    Integer getTotalCount();

    /**
     * 保存
     *
     * @param entity
     * @return
     */
    @PostMapping("/message/insert")
    Message insert(@RequestBody Message entity);

    /**
     * 修改
     *
     * @param entity
     * @return
     */
    @PutMapping("/message/update")
    Message update(@RequestBody Message entity);


    /**
     * 根据ID删除
     *
     * @param id
     */
    @DeleteMapping("/message/deleteById")
    void deleteById(@RequestParam("id") String id);

    /**
     * 批量删除
     *
     * @param ids
     */
    @DeleteMapping("/message/deleteBatchIds")
    void deleteBatchIds(@RequestParam("ids") List<String> ids);


    /**
     * 多条件分页获取
     *
     * @param messageCondition
     * @return
     */
    @PostMapping("/message/findByCondition")
    Page<Message> findByCondition(@RequestBody QueryCondition<Message> messageCondition);

    /**
     * 通过创建发送标识获取
     *
     * @param createSend
     * @return
     */
    @GetMapping("/message/findByCreateSend")
    List<Message> findByCreateSend(@RequestParam("createSend") Boolean createSend);

}