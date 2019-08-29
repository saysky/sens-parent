package com.liuyanzhao.sens.user.core.service;

import com.liuyanzhao.sens.common.constant.CommonConstant;
import com.liuyanzhao.sens.user.api.entity.Role;
import com.liuyanzhao.sens.user.api.entity.User;
import com.liuyanzhao.sens.user.api.entity.UserRole;
import com.liuyanzhao.sens.user.api.service.UserRoleService;
import com.liuyanzhao.sens.user.core.mapper.UserMapper;
import com.liuyanzhao.sens.user.core.mapper.UserRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户角色接口实现
 *
 * @author liuyanzhao
 */
@Slf4j
@RestController
@Transactional
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserRole> findByRoleId(String roleId) {
        return userRoleMapper.findByRoleId(roleId);
    }

    @Override
    public List<User> findUserByRoleId(String roleId) {

        List<UserRole> userRoleList = userRoleMapper.findByRoleId(roleId);
        List<User> list = new ArrayList<>();
        for (UserRole ur : userRoleList) {
            User u = userMapper.selectById(ur.getUserId());
            if (u != null && CommonConstant.USER_STATUS_NORMAL.equals(u.getStatus())) {
                list.add(u);
            }
        }
        return list;
    }

    @Override
    public void deleteByUserId(String userId) {
        userRoleMapper.deleteByUserId(userId);
    }

    @Override
    public List<Role> findByUserId(String userId) {
        return userRoleMapper.findByUserId(userId);
    }

    @Override
    public UserRole findById(String id) {
        return userRoleMapper.selectById(id);
    }

    @Override
    public Integer getTotalCount() {
        return userRoleMapper.selectCount(null);
    }

    @Override
    public UserRole insert(UserRole entity) {
         userRoleMapper.insert(entity);
         return entity;
    }

    @Override
    public UserRole update(UserRole entity) {
        userRoleMapper.updateById(entity);
        return entity;
    }

    @Override
    public void deleteById(String id) {
        userRoleMapper.deleteById(id);
    }

    @Override
    public void deleteBatchIds(List<String> ids) {
        userRoleMapper.deleteBatchIds(ids);

    }
}
