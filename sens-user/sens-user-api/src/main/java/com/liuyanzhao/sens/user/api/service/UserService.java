package com.liuyanzhao.sens.user.api.service;

import com.liuyanzhao.sens.user.api.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/user/{id}")
    User getUserById(@PathVariable("id") Long id);

    /**
     * 根据username查询
     *
     * @param username
     * @return
     */
    @GetMapping("/user/findByUsername")
    User getUserByUsername(@RequestParam("username") String username);

    /**
     * 根据用户ID删除用户
     * 返回是否删除成功
     *
     * @param id 用户
     * @return 是否删除成功
     */
    @DeleteMapping("/user/{id}")
    Boolean deleteUserById(@PathVariable("id") Integer id);

    /**
     * 更新用户
     *
     * @param user 用户
     * @return 是否删除成功
     */
    @PutMapping("/user")
    Boolean updateUser(@RequestBody User user);

    /**
     * 添加新用户
     *
     * @param user 用户
     * @return 是否删除成功
     */
    @PostMapping("/user")
    Boolean insertUser(@RequestBody User user);

}
