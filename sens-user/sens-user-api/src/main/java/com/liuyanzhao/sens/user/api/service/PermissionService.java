package com.liuyanzhao.sens.user.api.service;

import com.liuyanzhao.sens.user.api.entity.Permission;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 权限接口
 * @author 言曌
 * @date 2019-08-12 12:53
 */
@FeignClient(name = "sens-user-core")
public interface PermissionService {

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
}