package com.liuyanzhao.sens.user.web.util;

import cn.hutool.core.bean.BeanUtil;
import com.liuyanzhao.sens.user.api.entity.Permission;
import com.liuyanzhao.sens.user.web.vo.MenuVo;

/**
 * @author liuyanzhao
 */
public class VoUtil {

    public static MenuVo permissionToMenuVo(Permission p){

        MenuVo menuVo = new MenuVo();
        BeanUtil.copyProperties(p, menuVo);
        return menuVo;
    }
}
