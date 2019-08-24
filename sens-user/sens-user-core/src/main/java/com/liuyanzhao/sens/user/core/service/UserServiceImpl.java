package com.liuyanzhao.sens.user.core.service;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuyanzhao.sens.common.dto.QueryCondition;
import com.liuyanzhao.sens.common.vo.SearchVo;
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
    public User findByUsername(String username) {
        User user = userMapper.selectOne(
                new QueryWrapper<User>().eq("username", username)
        );
        if(user != null) {
            // 关联角色
            List<Role> roleList = roleMapper.findByUserId(user.getId());
            user.setRoles(roleList);
            // 关联权限菜单
            List<Permission> permissionList = permissionMapper.findByUserId(user.getId());
            user.setPermissions(permissionList);
        }
        return user;
    }

    @Override
    public User findByMobile(String mobile) {
        User user = userMapper.selectOne(
                new QueryWrapper<User>().eq("mobile", mobile)
        );
        return user;
    }

    @Override
    public User findByEmail(String email) {
        User user = userMapper.selectOne(
                new QueryWrapper<User>().eq("email", email)
        );
        return user;
    }

    @Override
    public Page<User> findByCondition(QueryCondition<User> userCondition) {
        User user = userCondition.getData();
        Page page = userCondition.getPage();
        SearchVo searchVo = userCondition.getSearchVo();

        //对指定字段查询
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

        //查询日期范围
        if (searchVo != null) {
            String startDate = searchVo.getStartDate();
            String endDate = searchVo.getEndDate();
            if (startDate != null && endDate != null) {
                Date start = DateUtil.parse(startDate);
                Date end = DateUtil.parse(endDate);
                queryWrapper.between("create_time", start, end);
            }
        }
        Page<User> userPage = (Page<User>) userMapper.selectPage(page, queryWrapper);
        return userPage;
    }


    @Override
    public User findById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public Integer getTotalCount() {
        return userMapper.selectCount(null);
    }

    @Override
    public User insert(User entity) {
        userMapper.insert(entity);
        return entity;
    }

    @Override
    public User update(User entity) {
        userMapper.updateById(entity);
        return entity;
    }

    @Override
    public void deleteById(Long id) {
        userMapper.deleteById(id);
    }

    @Override
    public void deleteBatchIds(List<Long> ids) {
        deleteBatchIds(ids);
    }
}
