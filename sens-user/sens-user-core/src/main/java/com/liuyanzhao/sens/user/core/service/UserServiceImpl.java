package com.liuyanzhao.sens.user.core.service;

import com.liuyanzhao.sens.user.api.entity.User;
import com.liuyanzhao.sens.user.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 言曌
 * @date 2019-08-07 08:45
 */
@RestController
@Slf4j
public class UserServiceImpl implements UserService {

    @Override
    @GetMapping("/user/{id}")
    public User getUserById(Long id) {
        User test = new User();
        test.setId(id);
        test.setUsername("test");
        test.setNickName("测试Feign功能");
        return test;
    }

    @Override
    public User getUserByUsername(String username) {
        return null;
    }

    @Override
    public Boolean deleteUserById(Integer id) {
        return null;
    }

    @Override
    public Boolean updateUser(User user) {
        return null;
    }

    @Override
    public Boolean insertUser(User user) {
        return null;
    }
}
