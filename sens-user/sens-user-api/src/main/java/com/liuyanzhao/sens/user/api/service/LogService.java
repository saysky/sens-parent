package com.liuyanzhao.sens.user.api.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuyanzhao.sens.common.dto.QueryCondition;
import com.liuyanzhao.sens.user.api.entity.Log;
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
public interface LogService {

    /**
     * 根据ID获取
     *
     * @param id
     * @return
     */
    @GetMapping("/log/findById")
    Log findById(@RequestParam("id") String id);

    /**
     * 获取总数
     *
     * @return
     */
    @GetMapping("/log/getTotalCount")
    Integer getTotalCount();

    /**
     * 添加
     *
     * @param entity
     * @return
     */
    @PostMapping("/log/insert")
    Log insert(@RequestBody Log entity);

    /**
     * 修改
     *
     * @param entity
     * @return
     */
    @PutMapping("/log/update")
    Log update(@RequestBody Log entity);


    /**
     * 根据ID删除
     *
     * @param id
     */
    @DeleteMapping("/log/deleteById")
    void deleteById(@RequestParam("id") String id);

    /**
     * 批量删除
     *
     * @param ids
     */
    @DeleteMapping("/log/deleteBatchIds")
    void deleteBatchIds(@RequestParam("ids") List<String> ids);


    /**
     * 多条件分页获取
     *
     * @param logCondition
     * @return
     */
    @PostMapping("/log/findByCondition")
    Page<Log> findByCondition(@RequestBody QueryCondition<Log> logCondition);

    /**
     * 删除所有
     */
    @DeleteMapping("/log/deleteAll")
    void deleteAll();

}