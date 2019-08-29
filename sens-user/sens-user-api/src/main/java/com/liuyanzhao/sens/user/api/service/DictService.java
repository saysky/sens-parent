package com.liuyanzhao.sens.user.api.service;

import com.liuyanzhao.sens.user.api.entity.Dict;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 言曌
 * @date 2019-08-25 17:12
 */
@FeignClient(name = "sens-user-core")
public interface DictService {

    /**
     * 根据ID获取
     *
     * @param id
     * @return
     */
    @GetMapping("/dict/findById")
    Dict findById(@RequestParam("id") String id);

    /**
     * 获取总数
     *
     * @return
     */
    @GetMapping("/dict/getTotalCount")
    Integer getTotalCount();

    /**
     * 添加
     *
     * @param entity
     * @return
     */
    @PostMapping("/dict/insert")
    Dict insert(@RequestBody Dict entity);

    /**
     * 修改
     *
     * @param entity
     * @return
     */
    @PutMapping("/dict/update")
    Dict update(@RequestBody Dict entity);


    /**
     * 根据ID删除
     *
     * @param id
     */
    @DeleteMapping("/dict/deleteById")
    void deleteById(@RequestParam("id") String id);

    /**
     * 批量删除
     *
     * @param ids
     */
    @DeleteMapping("/dict/deleteBatchIds")
    void deleteBatchIds(@RequestParam("ids") List<String> ids);
    
    /**
     * 排序获取全部
     * @return
     */
    @GetMapping("/dict/findAllOrderBySortOrder")
    List<Dict> findAllOrderBySortOrder();

    /**
     * 通过type获取
     * @param type
     * @return
     */
    @GetMapping("/dict/findByType")
    Dict findByType(@RequestParam("type") String type);

    /**
     * 模糊搜索
     * @param key
     * @return
     */
    @GetMapping("/dict/findByTitleOrTypeLike")
    List<Dict> findByTitleOrTypeLike(@RequestParam("key") String key);
}
