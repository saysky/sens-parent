package com.liuyanzhao.sens.user.api.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuyanzhao.sens.common.dto.QueryCondition;
import com.liuyanzhao.sens.user.api.entity.DictData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 言曌
 * @date 2019-08-25 17:12
 */
@FeignClient(name = "sens-user-core")
public interface DictDataService {

    /**
     * 根据ID获取
     *
     * @param id
     * @return
     */
    @GetMapping("/dictData/findById")
    DictData findById(@RequestParam("id") String id);

    /**
     * 获取总数
     *
     * @return
     */
    @GetMapping("/dictData/getTotalCount")
    Integer getTotalCount();

    /**
     * 添加
     *
     * @param entity
     * @return
     */
    @PostMapping("/dictData/insert")
    DictData insert(@RequestBody DictData entity);

    /**
     * 修改
     *
     * @param entity
     * @return
     */
    @PutMapping("/dictData/update")
    DictData update(@RequestBody DictData entity);


    /**
     * 根据ID删除
     *
     * @param id
     */
    @DeleteMapping("/dictData/deleteById")
    void deleteById(@RequestParam("id") String id);

    /**
     * 批量删除
     *
     * @param ids
     */
    @DeleteMapping("/dictData/deleteBatchIds")
    void deleteBatchIds(@RequestParam("ids") List<String> ids);

    /**
     * 多条件获取
     *
     * @param queryCondition
     * @return
     */
    @PostMapping("/dictData/findByCondition")
    Page<DictData> findByCondition(@RequestBody QueryCondition<DictData> queryCondition);

    /**
     * 通过dictId获取启用字典 已排序
     *
     * @param dictId
     * @return
     */
    @GetMapping("/dictData/findByDictId")
    List<DictData> findByDictId(@RequestParam("dictId") String dictId);

    /**
     * 通过dictId删除
     *
     * @param dictId
     */
    @DeleteMapping("/dictData/deleteByDictId")
    void deleteByDictId(@RequestParam("dictId") String dictId);
}
