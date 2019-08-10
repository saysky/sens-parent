package com.liuyanzhao.sens.user.core.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.liuyanzhao.sens.user.api.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 言曌
 * @date 2019-08-09 15:15
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 查询所有
     *
     * @return 用户列表
     */
    List<User> findAll(Pagination page);

    /**
     * 根据用户名获得用户
     *
     * @param username 用户名
     * @return 用户
     */
    User findByUsername(String username);

    /**
     * 通过手机获取用户
     * @param mobile
     * @return
     */
    User findByMobile(String mobile);

    /**
     * 通过邮件获取用户
     * @param email
     * @return
     */
    User findByEmail(String email);

    /**
     * 通过用户名模糊搜索
     * @param username
     * @param status
     * @return
     */
    List<User> findByUsernameLikeAndStatus(@Param("username") String username,
                                           @Param("status") Integer status);
}
