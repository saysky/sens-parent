package com.liuyanzhao.sens.user.api.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuyanzhao.sens.common.dto.QueryCondition;
import com.liuyanzhao.sens.user.api.entity.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 言曌
 * @date 2019-08-07 08:46
 */
@FeignClient(name = "sens-user-core")
public interface UserService  {

    /**
     * 根据ID获取
     *
     * @param id
     * @return
     */
    @GetMapping("/user/findById")
    User findById(@RequestParam("id") String id);

    /**
     * 获取总数
     *
     * @return
     */
    @GetMapping("/user/getTotalCount")
    Integer getTotalCount();

    /**
     * 保存
     *
     * @param entity
     * @return
     */
    @PostMapping("/user/insert")
    User insert(@RequestBody User entity);

    /**
     * 修改
     *
     * @param entity
     * @return
     */
    @PutMapping("/user/update")
    User update(@RequestBody User entity);


    /**
     * 根据ID删除
     *
     * @param id
     */
    @DeleteMapping("/user/deleteById")
    void deleteById(@RequestParam("id") String id);

    /**
     * 批量删除
     *
     * @param ids
     */
    @DeleteMapping("/user/deleteBatchIds")
    void deleteBatchIds(@RequestParam("ids") List<String> ids);
    
    /**
     * 通过用户名获取用户
     * @param username
     * @return
     */
    @Cacheable(key = "#username")
    @GetMapping("/user/findByUsername")
    User findByUsername(@RequestParam("username") String username);

    /**
     * 通过手机获取用户
     * @param mobile
     * @return
     */
    @GetMapping("/user/findByMobile")
    User findByMobile(@RequestParam("mobile") String mobile);

    /**
     * 通过邮件和状态获取用户
     * @param email
     * @return
     */
    @GetMapping("/user/findByEmail")
    User findByEmail(@RequestParam("email") String email);

    /**
     * 多条件分页获取用户
     * @param userCondition
     * @return
     */
    @PostMapping("/user/findByCondition")
    Page<User> findByCondition(@RequestBody QueryCondition<User> userCondition);

}
