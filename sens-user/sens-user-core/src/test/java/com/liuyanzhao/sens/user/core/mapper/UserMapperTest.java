package com.liuyanzhao.sens.mapper;

import com.liuyanzhao.sens.user.core.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author 言曌
 * @date 2019/4/5 上午11:30
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void findByUserId() throws Exception {
//        List<Category> categoryList = categoryMapper.findByUserId(1L);
//        System.out.println(categoryList);
    }

}