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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户角色接口实现
 *
 * @author Exrickx
 */
@Slf4j
@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserRole> findByRoleId(Long roleId) {
        return userRoleMapper.findByRoleId(roleId);
    }

    @Override
    public List<User> findUserByRoleId(Long roleId) {

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
    public void deleteByUserId(Long userId) {
        userRoleMapper.deleteByUserId(userId);
    }

    @Override
    public List<Role> findByUserId(Long userId) {
        return userRoleMapper.findByUserId(userId);
    }

    @Override
    public UserRole findById(Long id) {
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
    public void deleteById(Long id) {
        userRoleMapper.deleteById(id);
    }

    @Override
    public void deleteBatchIds(List<Long> ids) {
        userRoleMapper.deleteBatchIds(ids);

    }
}
