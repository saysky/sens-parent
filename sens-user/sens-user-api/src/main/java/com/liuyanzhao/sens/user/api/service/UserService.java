package com.liuyanzhao.sens.user.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuyanzhao.sens.common.vo.Response;
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
public interface UserService {

    /**
     * 根据用户ID获得用户信息
     *
     * @param id
     * @return
     */
    @GetMapping("/user/getUserById")
    Response<User> getUserById(@PathVariable("id") Long id);

    /**
     * 根据用户ID删除用户
     * 返回是否删除成功
     *
     * @param id 用户
     * @return 是否删除成功
     */
    @DeleteMapping("/user/deleteUserById")
    Response<Boolean> deleteUserById(@PathVariable("id") Integer id);

    /**
     * 更新用户
     *
     * @param user 用户
     * @return 是否删除成功
     */
    @PutMapping("/user/updateUser")
    Response<Boolean> updateUser(@RequestBody User user);

    /**
     * 添加新用户
     *
     * @param user 用户
     * @return 是否删除成功
     */
    @PostMapping("/user/insertUser")
    Response<Boolean> insertUser(@RequestBody User user);

    /**
     * 通过用户名获取用户
     * @param username
     * @return
     */
    @Cacheable(key = "#username")
    @GetMapping("/user/findByUsername")
    Response<User> findByUsername(String username);

    /**
     * 通过手机获取用户
     * @param mobile
     * @return
     */
    @GetMapping("/user/findByMobile")
    Response<User> findByMobile(String mobile);

    /**
     * 通过邮件和状态获取用户
     * @param email
     * @return
     */
    @GetMapping("/user/findByEmail")
    Response<User> findByEmail(String email);

    /**
     * 多条件分页获取用户
     * @param user
     * @param page
     * @return
     */
    @GetMapping("/user/findByCondition")
    Response<IPage<User>> findByCondition(User user, Page<User> page);

}
