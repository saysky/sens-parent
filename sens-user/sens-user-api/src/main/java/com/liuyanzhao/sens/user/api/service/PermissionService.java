package com.liuyanzhao.sens.user.api.service;

import com.liuyanzhao.sens.user.api.entity.Permission;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 权限接口
 * @author 言曌
 * @date 2019-08-12 12:53
 */
@FeignClient(name = "sens-user-core")
public interface PermissionService {


    /**
     * 根据ID获取
     *
     * @param id
     * @return
     */
    @GetMapping("/permission/findById")
    Permission findById(@RequestParam("id") String id);

    /**
     * 获取总数
     *
     * @return
     */
    @GetMapping("/permission/getTotalCount")
    Integer getTotalCount();

    /**
     * 保存
     *
     * @param entity
     * @return
     */
    @PostMapping("/permission/insert")
    Permission insert(@RequestBody Permission entity);

    /**
     * 修改
     *
     * @param entity
     * @return
     */
    @PutMapping("/permission/update")
    Permission update(@RequestBody Permission entity);


    /**
     * 根据ID删除
     *
     * @param id
     */
    @DeleteMapping("/permission/deleteById")
    void deleteById(@RequestParam("id") String id);

    /**
     * 批量删除
     *
     * @param ids
     */
    @DeleteMapping("/permission/deleteBatchIds")
    void deleteBatchIds(@RequestParam("ids") List<String> ids);



    /**
     * 通过层级查找
     * 默认升序
     * @param level
     * @return
     */
    @GetMapping("/permission/findByLevelOrderBySortOrder")
    List<Permission> findByLevelOrderBySortOrder(@RequestParam("level") Integer level);

    /**
     * 通过parendId查找
     * @param parentId
     * @return
     */
    @GetMapping("/permission/findByParentIdOrderBySortOrder")
    List<Permission> findByParentIdOrderBySortOrder(@RequestParam("parentId") String parentId);

    /**
     * 通过类型和状态获取
     * @param type
     * @param status
     * @return
     */
    @GetMapping("/permission/findByTypeAndStatusOrderBySortOrder")
    List<Permission> findByTypeAndStatusOrderBySortOrder(@RequestParam("type") Integer type,
                                                         @RequestParam("status") Integer status);

    /**
     * 通过名称获取
     * @param title
     * @return
     */
    @GetMapping("/permission/findByTitle")
    List<Permission> findByTitle(@RequestParam("title") String title);

    /**
     * 模糊搜索
     * @param title
     * @return
     */
    @GetMapping("/permission/findByTitleLikeOrderBySortOrder")
    List<Permission> findByTitleLikeOrderBySortOrder(@RequestParam("title") String title);

    /**
     * 通过用户id获取
     * @param userId
     * @return
     */
    @Cacheable(value = "user::userPermission", key = "#userId")
    @GetMapping("/permission/findByUserId")
    List<Permission> findByUserId(@RequestParam("userId") String userId);
}