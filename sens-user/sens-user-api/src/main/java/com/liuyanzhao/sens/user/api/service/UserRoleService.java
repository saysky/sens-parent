package com.liuyanzhao.sens.user.api.service;

import com.liuyanzhao.sens.user.api.entity.Role;
import com.liuyanzhao.sens.user.api.entity.User;
import com.liuyanzhao.sens.user.api.entity.UserRole;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 言曌
 * @date 2019-08-07 08:46
 */
@FeignClient(name = "sens-user-core")
public interface UserRoleService  {


    /**
     * 根据ID获取
     *
     * @param id
     * @return
     */
    @GetMapping("/userRole/findById")
    UserRole findById(@RequestParam("id") String id);

    /**
     * 获取总数
     *
     * @return
     */
    @GetMapping("/userRole/getTotalCount")
    Integer getTotalCount();

    /**
     * 保存
     *
     * @param entity
     * @return
     */
    @PostMapping("/userRole/save")
    UserRole insert(@RequestBody UserRole entity);

    /**
     * 修改
     *
     * @param entity
     * @return
     */
    @PutMapping("/userRole/update")
    UserRole update(@RequestBody UserRole entity);


    /**
     * 根据ID删除
     *
     * @param id
     */
    @DeleteMapping("/userRole/deleteById")
    void deleteById(@RequestParam("id") String id);

    /**
     * 批量删除
     *
     * @param ids
     */
    @DeleteMapping("/userRole/deleteBatchIds")
    void deleteBatchIds(@RequestParam("ids") List<String> ids);


    /**
     * 通过roleId查找
     * @param roleId
     * @return
     */
    @GetMapping("/userRole/findByRoleId")
    List<UserRole> findByRoleId(@RequestParam("roleId") String roleId);

    /**
     * 通过roleId查找用户
     * @param roleId
     * @return
     */
    @GetMapping("/userRole/findUserByRoleId")
    List<User> findUserByRoleId(@RequestParam("roleId") String roleId);

    /**
     * 删除用户角色
     * @param userId
     */
    @DeleteMapping("/userRole/deleteByUserId")
    void deleteByUserId(@RequestParam("userId") String userId);

    /**
     * 通过用户id获取
     * @param userId
     * @return
     */
    @Cacheable(key = "#userId")
    @GetMapping("/userRole/findByUserId")
    List<Role> findByUserId(@RequestParam("userId") String userId);

}
