package com.liuyanzhao.sens.user.core.service;

import com.liuyanzhao.sens.user.api.entity.Role;
import com.liuyanzhao.sens.user.api.service.RoleService;
import com.liuyanzhao.sens.user.core.mapper.RoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 言曌
 * @date 2019-08-13 21:55
 */
@RestController
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Role findById(String id) {
        return roleMapper.selectById(id);
    }

    @Override
    public Integer getTotalCount() {
         return roleMapper.selectCount(null);
    }

    @Override
    public Role insert(Role entity) {
        roleMapper.insert(entity);
        return entity;
    }

    @Override
    public Role update(Role entity) {
        roleMapper.updateById(entity);
        return entity;
    }

    @Override
    public void deleteById(String id) {
        roleMapper.deleteById(id);
    }

    @Override
    public void deleteBatchIds(List<String> ids) {
        roleMapper.deleteBatchIds(ids);
    }

    @Override
    public List<Role> findByDefaultRole(Boolean defaultRole) {
        return roleMapper.findByDefaultRole(defaultRole);
    }

    @Override
    public List<Role> findByUserId(String userId) {
        return roleMapper.findByUserId(userId);
    }
}
