package com.liuyanzhao.sens.user.web.controller;

import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liuyanzhao.sens.common.constant.CommonConstant;
import com.liuyanzhao.sens.common.vo.Response;
import com.liuyanzhao.sens.user.api.entity.Permission;
import com.liuyanzhao.sens.user.api.entity.RolePermission;
import com.liuyanzhao.sens.user.api.entity.User;
import com.liuyanzhao.sens.user.api.service.PermissionService;
import com.liuyanzhao.sens.user.api.service.RolePermissionService;
import com.liuyanzhao.sens.user.web.config.security.permission.MySecurityMetadataSource;
import com.liuyanzhao.sens.user.web.util.SecurityUtil;
import com.liuyanzhao.sens.user.web.util.VoUtil;
import com.liuyanzhao.sens.user.web.vo.MenuVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 菜单/权限管理接口
 *
 * @author liuyanzhao
 */
@Slf4j
@RestController
@RequestMapping("/permission")
@CacheConfig(cacheNames = "permission")
@Transactional
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private MySecurityMetadataSource mySecurityMetadataSource;

    /**
     * 获取用户页面菜单数据
     *
     * @return
     */
    @RequestMapping(value = "/getMenuList", method = RequestMethod.GET)
    public Response<List<MenuVo>> getAllMenuList() {

        List<MenuVo> menuList = new ArrayList<>();
        // 读取缓存
        User u = securityUtil.getCurrUser();
        String key = "permission::userMenuList:" + u.getId();
        String v = redisTemplate.opsForValue().get(key);
        if (StrUtil.isNotBlank(v)) {
            menuList = new Gson().fromJson(v, new TypeToken<List<MenuVo>>() {
            }.getType());
            return Response.yes(menuList);
        }

        // 用户所有权限 已排序去重
        List<Permission> list = permissionService.findByUserId(u.getId());

        // 筛选0级页面
        for (Permission p : list) {
            if (CommonConstant.PERMISSION_NAV.equals(p.getType()) && CommonConstant.LEVEL_ZERO.equals(p.getLevel())) {
                menuList.add(VoUtil.permissionToMenuVo(p));
            }
        }
        // 筛选一级页面
        List<MenuVo> firstMenuList = new ArrayList<>();
        for (Permission p : list) {
            if (CommonConstant.PERMISSION_PAGE.equals(p.getType()) && CommonConstant.LEVEL_ONE.equals(p.getLevel())) {
                firstMenuList.add(VoUtil.permissionToMenuVo(p));
            }
        }
        // 筛选二级页面
        List<MenuVo> secondMenuList = new ArrayList<>();
        for (Permission p : list) {
            if (CommonConstant.PERMISSION_PAGE.equals(p.getType()) && CommonConstant.LEVEL_TWO.equals(p.getLevel())) {
                secondMenuList.add(VoUtil.permissionToMenuVo(p));
            }
        }
        // 筛选二级页面拥有的按钮权限
        List<MenuVo> buttonPermissions = new ArrayList<>();
        for (Permission p : list) {
            if (CommonConstant.PERMISSION_OPERATION.equals(p.getType()) && CommonConstant.LEVEL_THREE.equals(p.getLevel())) {
                buttonPermissions.add(VoUtil.permissionToMenuVo(p));
            }
        }

        // 匹配二级页面拥有权限
        for (MenuVo m : secondMenuList) {
            List<String> permTypes = new ArrayList<>();
            for (MenuVo me : buttonPermissions) {
                if (m.getId().equals(me.getParentId())) {
                    permTypes.add(me.getButtonType());
                }
            }
            m.setPermTypes(permTypes);
        }
        // 匹配一级页面拥有二级页面
        for (MenuVo m : firstMenuList) {
            List<MenuVo> secondMenu = new ArrayList<>();
            for (MenuVo me : secondMenuList) {
                if (m.getId().equals(me.getParentId())) {
                    secondMenu.add(me);
                }
            }
            m.setChildren(secondMenu);
        }
        // 匹配0级页面拥有一级页面
        for (MenuVo m : menuList) {
            List<MenuVo> firstMenu = new ArrayList<>();
            for (MenuVo me : firstMenuList) {
                if (m.getId().equals(me.getParentId())) {
                    firstMenu.add(me);
                }
            }
            m.setChildren(firstMenu);
        }

        // 缓存
        redisTemplate.opsForValue().set(key, new Gson().toJson(menuList));
        return Response.yes(menuList);
    }

    /**
     * 获取权限菜单树
     *
     * @return
     */
    @RequestMapping(value = "/getAllList", method = RequestMethod.GET)
    @Cacheable(key = "'allList'")
    public Response<List<Permission>> getAllList() {

        // 0级
        List<Permission> list0 = permissionService.findByLevelOrderBySortOrder(CommonConstant.LEVEL_ZERO);
        for (Permission p0 : list0) {
            // 一级
            List<Permission> list1 = permissionService.findByParentIdOrderBySortOrder(p0.getId());
            p0.setChildren(list1);
            // 二级
            for (Permission p1 : list1) {
                List<Permission> children1 = permissionService.findByParentIdOrderBySortOrder(p1.getId());
                p1.setChildren(children1);
                // 三级
                for (Permission p2 : children1) {
                    List<Permission> children2 = permissionService.findByParentIdOrderBySortOrder(p2.getId());
                    p2.setChildren(children2);
                }
            }
        }
        return Response.yes(list0);
    }

    /**
     * 添加
     *
     * @param permission
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @CacheEvict(key = "'menuList'")
    public Response<Permission> add(@ModelAttribute Permission permission) {

        // 判断拦截请求的操作权限按钮名是否已存在
        if (CommonConstant.PERMISSION_OPERATION.equals(permission.getType())) {
            List<Permission> list = permissionService.findByTitle(permission.getTitle());
            if (list != null && list.size() > 0) {
                return Response.no("名称已存在");
            }
        }
        Permission u = permissionService.insert(permission);
        //重新加载权限
        mySecurityMetadataSource.loadResourceDefine();
        //手动删除缓存
        redisTemplate.delete("permission::allList");
        return Response.yes(u);
    }

    /**
     * 编辑
     *
     * @param permission
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public Response<Permission> edit(@ModelAttribute Permission permission) {

        // 判断拦截请求的操作权限按钮名是否已存在
        if (CommonConstant.PERMISSION_OPERATION.equals(permission.getType())) {
            // 若名称修改
            Permission p = permissionService.findById(permission.getId());
            if (!p.getTitle().equals(permission.getTitle())) {
                List<Permission> list = permissionService.findByTitle(permission.getTitle());
                if (list != null && list.size() > 0) {
                    return Response.no("名称已存在");
                }
            }
        }
        Permission u = permissionService.update(permission);
        //重新加载权限
        mySecurityMetadataSource.loadResourceDefine();
        //手动批量删除缓存
        Set<String> keys = redisTemplate.keys("userPermission:" + "*");
        redisTemplate.delete(keys);
        Set<String> keysUser = redisTemplate.keys("user:" + "*");
        redisTemplate.delete(keysUser);
        Set<String> keysUserMenu = redisTemplate.keys("permission::userMenuList:*");
        redisTemplate.delete(keysUserMenu);
        redisTemplate.delete("permission::allList");
        return Response.yes(u);
    }

    /**
     * 批量通过id删除
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delByIds/{ids}", method = RequestMethod.DELETE)
    @CacheEvict(key = "'menuList'")
    public Response<Object> delByIds(@PathVariable String[] ids) {

        for (String id : ids) {
            List<RolePermission> list = rolePermissionService.findByPermissionId(id);
            if (list != null && list.size() > 0) {
                return Response.no("删除失败，包含正被角色使用关联的菜单或权限");
            }
        }
        for (String id : ids) {
            permissionService.deleteById(id);
        }
        //重新加载权限
        mySecurityMetadataSource.loadResourceDefine();
        //手动删除缓存
        redisTemplate.delete("permission::allList");
        return Response.yes("批量通过id删除数据成功");
    }

    /**
     * 搜索菜单
     * @param title
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Response<List<Permission>> searchPermissionList(@RequestParam String title) {

        List<Permission> list = permissionService.findByTitleLikeOrderBySortOrder("%" + title + "%");
        return Response.yes(list);
    }
}
