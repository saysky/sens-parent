package com.liuyanzhao.sens.user.api.service;

import com.liuyanzhao.sens.user.api.entity.Role;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色接口
 * @author 言曌
 * @date 2019-08-12 12:53
 */
@FeignClient(name = "sens-user-core")
public interface RoleService {

    /**
     * 根据ID获取
     *
     * @param id
     * @return
     */
    @GetMapping("/role/findById")
    Role findById(@RequestParam("id") String id);

    /**
     * 获取总数
     *
     * @return
     */
    @GetMapping("/role/getTotalCount")
    Integer getTotalCount();

    /**
     * 保存
     *
     * @param entity
     * @return
     */
    @PostMapping("/role/insert")
    Role insert(@RequestBody Role entity);

    /**
     * 修改
     *
     * @param entity
     * @return
     */
    @PutMapping("/role/update")
    Role update(@RequestBody Role entity);


    /**
     * 根据ID删除
     *
     * @param id
     */
    @DeleteMapping("/role/deleteById")
    void deleteById(@RequestParam("id") String id);

    /**
     * 批量删除
     *
     * @param ids
     */
    @DeleteMapping("/role/deleteBatchIds")
    void deleteBatchIds(@RequestParam("ids") List<String> ids);
    
    /**
     * 获取默认角色
     * @param defaultRole
     * @return
     */
    @GetMapping("/role/findByDefaultRole")
    List<Role> findByDefaultRole(@RequestParam("defaultRole") Boolean defaultRole);

    /**
     * 通过用户id获取
     * @param userId
     * @return
     */
    @Cacheable(value = "user::userRole", key = "#userId")
    @GetMapping("/role/findByUserId")
    List<Role> findByUserId(String userId);
}
