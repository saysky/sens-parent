package com.liuyanzhao.sens.user.core.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuyanzhao.sens.user.api.entity.User;
import com.liuyanzhao.sens.user.core.SensUserCoreApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


/**
 * @author 言曌
 * @date 2019/4/5 上午11:30
 */

@SpringBootTest(classes = SensUserCoreApplication.class)
@RunWith(SpringRunner.class)
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    /**
     * 测试根据ID删除
     */
    @Test
    public void testDeleteById() {
        userMapper.deleteById(1L);
    }

    /**
     * 测试根据ID列表批量删除
     */
    @Test
    public void testDeleteBatchIds() {
        List<String> list = new ArrayList<>(2);
        list.add("3");
        list.add("4");
        userMapper.deleteBatchIds(list);
    }

    /**
     * 测试根据ID查询
     */
    @Test
    public void testSelectById() {
        User user = userMapper.selectById("1");
        System.out.println(user);
    }

    /**
     * 测试新增
     */
    @Test
    public void testInsert() {
        User user = new User();
        user.setUsername("zhangsan");
        user.setNickname("张三");
        user.setPassword("123456");
        userMapper.insert(user);
    }

    /**
     * 测试分页查询
     */
    @Test
    public void testSelectPage() {
        Page<User> page = new Page<>(1, 10);
        userMapper.selectPage(page, new QueryWrapper<User>().eq("status", 0));
        System.out.println(page);
    }


}