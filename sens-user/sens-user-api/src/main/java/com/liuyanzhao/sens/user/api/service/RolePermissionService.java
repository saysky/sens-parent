package com.liuyanzhao.sens.user.api.service;

import com.liuyanzhao.sens.user.api.entity.RolePermission;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 言曌
 * @date 2019-08-07 08:46
 */
@FeignClient(name = "sens-user-core")
public interface RolePermissionService {

    /**
     * 根据ID获取
     *
     * @param id
     * @return
     */
    @GetMapping("/rolePermission/findById")
    RolePermission findById(@RequestParam("id") String id);

    /**
     * 获取总数
     *
     * @return
     */
    @GetMapping("/rolePermission/getTotalCount")
    Integer getTotalCount();

    /**
     * 保存
     *
     * @param entity
     * @return
     */
    @PostMapping("/rolePermission/insert")
    RolePermission insert(@RequestBody RolePermission entity);

    /**
     * 修改
     *
     * @param entity
     * @return
     */
    @PutMapping("/rolePermission/update")
    RolePermission update(@RequestBody RolePermission entity);


    /**
     * 根据ID删除
     *
     * @param id
     */
    @DeleteMapping("/rolePermission/deleteById")
    void deleteById(@RequestParam("id") String id);

    /**
     * 批量删除
     *
     * @param ids
     */
    @DeleteMapping("/rolePermission/deleteBatchIds")
    void deleteBatchIds(@RequestParam("ids") List<String> ids);


    /**
     * 通过permissionId获取
     *
     * @param permissionId
     * @return
     */
    @GetMapping("/rolePermission/findByPermissionId")
    List<RolePermission> findByPermissionId(@RequestParam("permissionId") String permissionId);

    /**
     * 通过roleId获取
     *
     * @param roleId
     * @return
     */
    @GetMapping("/rolePermission/findByRoleId")
    List<RolePermission> findByRoleId(@RequestParam("roleId") String roleId);

    /**
     * 通过roleId删除
     *
     * @param roleId
     */
    @DeleteMapping("/rolePermission/deleteByRoleId")
    void deleteByRoleId(@RequestParam("roleId") String roleId);

}
