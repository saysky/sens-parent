package com.liuyanzhao.sens.user.core.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuyanzhao.sens.user.api.entity.Role;
import com.liuyanzhao.sens.user.api.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 通过用户id获取
     * @param userId
     * @return
     */
    List<Role> findByUserId(@Param("userId") String userId);
}
