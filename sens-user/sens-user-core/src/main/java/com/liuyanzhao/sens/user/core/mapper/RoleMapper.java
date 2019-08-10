package com.liuyanzhao.sens.user.core.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.liuyanzhao.sens.user.api.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 言曌
 * @date 2019-08-10 16:07
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 获取默认角色
     * @param defaultRole
     * @return
     */
    List<Role> findByDefaultRole(Boolean defaultRole);
}
