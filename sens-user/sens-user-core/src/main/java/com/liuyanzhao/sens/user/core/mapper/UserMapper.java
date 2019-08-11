package com.liuyanzhao.sens.user.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuyanzhao.sens.user.api.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 言曌
 * @date 2019-08-09 15:15
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
