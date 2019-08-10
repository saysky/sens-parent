package com.liuyanzhao.sens.user.core.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.liuyanzhao.sens.user.api.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 言曌
 * @date 2019-08-10 16:08
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 通过roleId查找
     * @param roleId
     * @return
     */
    List<UserRole> findByRoleId(String roleId);

    /**
     * 删除用户角色
     * @param userId
     */
    void deleteByUserId(String userId);
}
