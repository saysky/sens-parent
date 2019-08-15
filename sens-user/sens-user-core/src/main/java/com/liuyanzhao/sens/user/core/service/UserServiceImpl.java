package com.liuyanzhao.sens.user.core.service;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuyanzhao.sens.common.vo.Response;
import com.liuyanzhao.sens.common.vo.SearchVo;
import com.liuyanzhao.sens.user.api.dto.UserCondition;
import com.liuyanzhao.sens.user.api.entity.Permission;
import com.liuyanzhao.sens.user.api.entity.Role;
import com.liuyanzhao.sens.user.api.entity.User;
import com.liuyanzhao.sens.user.api.service.UserService;
import com.liuyanzhao.sens.user.core.mapper.PermissionMapper;
import com.liuyanzhao.sens.user.core.mapper.RoleMapper;
import com.liuyanzhao.sens.user.core.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author 言曌
 * @date 2019-08-07 08:45
 */
@RestController
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public Response<User> getUserById(Long id) {
        User user = userMapper.selectById(id);
        if(user == null) {
            return Response.no("用户不存在！");
        }
        return Response.yes(user);
    }


    @Override
    public Response<Boolean> deleteUserById(Long id) {
        int row = userMapper.deleteById(id);
        if(row == 0) {
            return Response.no("删除失败");
        }
        return Response.yes(true);
    }

    @Override
    public Response<Boolean> updateUser(User user) {
        int row = userMapper.updateById(user);
        if(row == 0) {
            return Response.no("更新失败");
        }
        return Response.yes(true);
    }

    @Override
    public Response<Boolean> insertUser(User user) {
        int row = userMapper.insert(user);
        if(row == 0) {
            return Response.no("添加失败");
        }
        return Response.yes();
    }

    @Override
    public Response<User> findByUsername(String username) {
        User user = userMapper.selectOne(
                new QueryWrapper<User>().eq("username", username)
        );
        if(user == null) {
            return Response.no("用户不存在！");
        }
        // 关联角色
        List<Role> roleList = roleMapper.findByUserId(user.getId());
        user.setRoles(roleList);
        // 关联权限菜单
        List<Permission> permissionList = permissionMapper.findByUserId(user.getId());
        user.setPermissions(permissionList);
        return Response.yes(user);
    }

    @Override
    public Response<User> findByMobile(String mobile) {
        User user = userMapper.selectOne(
                new QueryWrapper<User>().eq("mobile", mobile)
        );
        if(user == null) {
            return Response.no("用户不存在");
        }
        return Response.yes(user);
    }

    @Override
    public Response<User> findByEmail(String email) {
        User user = userMapper.selectOne(
                new QueryWrapper<User>().eq("email", email)
        );
        if(user == null) {
            return Response.no("用户不存在");
        }
        return Response.yes(user);
    }

    @Override
    public Response<Page<User>> findByCondition(UserCondition userCondition) {
        User user = userCondition.getUser();
        SearchVo searchVo = userCondition.getSearchVo();
        Page page = userCondition.getPage();

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (user != null) {
            if (user.getUsername() != null) {
                queryWrapper.like("username", user.getUsername());
            }
            if (user.getNickname() != null) {
                queryWrapper.like("nickname", user.getNickname());
            }
            if (user.getStatus() != null) {
                queryWrapper.eq("status", user.getStatus());
            }
            if (user.getType() != null) {
                queryWrapper.eq("type", user.getType());
            }
            if (user.getSex() != null) {
                queryWrapper.eq("sex", user.getSex());
            }
            if (user.getEmail() != null) {
                queryWrapper.eq("email", user.getEmail());
            }
            if (user.getMobile() != null) {
                queryWrapper.eq("mobile", user.getMobile());
            }
        }
        if (searchVo != null) {
            if (searchVo.getStartDate() != null && searchVo.getEndDate() != null) {
                Date start = DateUtil.parse(searchVo.getStartDate());
                Date end = DateUtil.parse(searchVo.getEndDate());
                queryWrapper.between("create_time", start, end);
            }
        }
        Page<User> userPage = (Page<User>) userMapper.selectPage(page, queryWrapper);
        return Response.yes(userPage);
    }
}
