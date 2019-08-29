package com.liuyanzhao.sens.user.core.service;

import com.liuyanzhao.sens.user.api.entity.RolePermission;
import com.liuyanzhao.sens.user.api.service.RolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 言曌
 * @date 2019-08-23 13:03
 */
@RestController
@Slf4j
public class RolePermissionServiceImpl implements RolePermissionService {
    @Override
    public List<RolePermission> findByPermissionId(String permissionId) {
        return null;
    }

    @Override
    public List<RolePermission> findByRoleId(String roleId) {
        return null;
    }

    @Override
    public void deleteByRoleId(String roleId) {

    }

    @Override
    public RolePermission findById(String id) {
        return null;
    }

    @Override
    public Integer getTotalCount() {
        return null;
    }

    @Override
    public RolePermission insert(RolePermission entity) {
        return null;
    }

    @Override
    public RolePermission update(RolePermission entity) {
        return null;
    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public void deleteBatchIds(List<String> ids) {

    }
}
