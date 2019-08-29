package com.liuyanzhao.sens.user.web.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuyanzhao.sens.common.annotation.SystemLog;
import com.liuyanzhao.sens.common.constant.CommonConstant;
import com.liuyanzhao.sens.common.dto.QueryCondition;
import com.liuyanzhao.sens.common.enums.LogType;
import com.liuyanzhao.sens.common.exception.ApiException;
import com.liuyanzhao.sens.common.util.PageUtil;
import com.liuyanzhao.sens.common.vo.PageVo;
import com.liuyanzhao.sens.common.vo.SearchVo;
import com.liuyanzhao.sens.user.api.entity.Role;
import com.liuyanzhao.sens.user.api.entity.User;
import com.liuyanzhao.sens.user.api.entity.UserRole;
import com.liuyanzhao.sens.user.api.service.RoleService;
import com.liuyanzhao.sens.user.api.service.UserRoleService;
import com.liuyanzhao.sens.user.api.service.UserService;
import com.liuyanzhao.sens.common.vo.Response;
import com.liuyanzhao.sens.user.web.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author 言曌
 * @date 2019-08-09 16:32
 */
@Slf4j
@CacheConfig(cacheNames = "user")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;


    @Autowired
    private StringRedisTemplate redisTemplate;


    /**
     * 获取当前登录用户接口
     *
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Response<User> getUserInfo() {
        User u = securityUtil.getCurrUser();
        u.setPassword(null);
        return Response.yes(u);
    }

    /**
     * 用户详细信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Response<User> getUserById(@PathVariable("id") String id) {
        User user = userService.findById(id);
        if (user == null) {
            return Response.no("用户不存在");
        }
        return Response.yes(user);
    }


    /**
     * 用户详细信息
     *
     * @param username
     * @return
     */
    @GetMapping("/username/{username}")
    public Response<User> getUserByName(@PathVariable("username") String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return Response.no("用户不存在");
        }
        return Response.yes(user);
    }

    /**
     * 用户详细信息
     *
     * @param
     * @return
     */
    @GetMapping
    public Response<Page<User>> findAll(@ModelAttribute User user,
                                        @ModelAttribute SearchVo searchVo,
                                        @ModelAttribute PageVo pageVo) {
        QueryCondition queryCondition = new QueryCondition(user, searchVo, PageUtil.initMpPage(pageVo));
        Page<User> userPage = userService.findByCondition(queryCondition);
        return Response.yes(userPage);
    }


    /**
     * 短信登录接口
     *
     * @param mobile
     * @param insertLogin
     * @return
     */
    @RequestMapping(value = "/smsLogin", method = RequestMethod.POST)
    @SystemLog(description = "短信登录", type = LogType.LOGIN)
    public Response<Object> smsLogin(@RequestParam String mobile,
                                     @RequestParam(required = false) Boolean insertLogin) {
        User u = userService.findByMobile(mobile);
        if (u == null) {
            throw new ApiException("手机号不存在");
        }
        String accessToken = securityUtil.getToken(u.getUsername(), insertLogin);
        return Response.yes(accessToken);
    }

    /**
     * 通过短信重置密码
     *
     * @param mobile
     * @param password
     * @return
     */
    @RequestMapping(value = "/resetByMobile", method = RequestMethod.POST)
    public Response<String> resetByMobile(@RequestParam String mobile,
                                          @RequestParam String password) {

        User u = userService.findByMobile(mobile);
        String encryptPass = new BCryptPasswordEncoder().encode(password);
        u.setPassword(encryptPass);
        userService.update(u);
        // 删除缓存
        redisTemplate.delete("user::" + u.getUsername());
        return Response.yes("重置密码成功");
    }

    /**
     * 注册用户
     *
     * @param u
     * @return
     */
    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    public Response<Object> regist(@ModelAttribute User u) {

        if (StrUtil.isBlank(u.getUsername()) || StrUtil.isBlank(u.getPassword())) {
            return Response.no("缺少必需表单字段");
        }

        if (userService.findByUsername(u.getUsername()) != null) {
            return Response.no("该用户名已被注册");
        }

        if (userService.findByMobile(u.getMobile()) != null) {
            return Response.no("该手机号已被注册");
        }

        String encryptPass = new BCryptPasswordEncoder().encode(u.getPassword());
        u.setPassword(encryptPass);
        u.setType(CommonConstant.USER_TYPE_NORMAL);
        User user = userService.insert(u);
        if (user == null) {
            return Response.no("注册失败");
        }
        // 默认角色
        List<Role> roleList = roleService.findByDefaultRole(true);
        if (roleList != null && roleList.size() > 0) {
            for (Role role : roleList) {
                UserRole ur = new UserRole();
                ur.setUserId(user.getId());
                ur.setRoleId(role.getId());
                userRoleService.insert(ur);
            }
        }
        // 异步发送创建账号消息

        return Response.yes(user);
    }


    /**
     * 修改绑定手机
     *
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/changeMobile", method = RequestMethod.POST)
    public Response<Object> changeMobile(@RequestParam String mobile) {

        if (userService.findByMobile(mobile) != null) {
            return Response.no("该手机号已绑定其他账户");
        }
        User u = securityUtil.getCurrUser();
        u.setMobile(mobile);
        userService.update(u);
        // 删除缓存
        redisTemplate.delete("user::" + u.getUsername());
        return Response.yes("修改手机号成功");
    }


    /**
     * 重置密码
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/resetPass", method = RequestMethod.POST)
    public Response<Object> resetPass(@RequestParam String[] ids) {

        for (String id : ids) {
            User u = userService.findById(id);
            // 在线DEMO所需
            if ("test".equals(u.getUsername()) || "test2".equals(u.getUsername()) || "admin".equals(u.getUsername())) {
                throw new ApiException("测试账号及管理员账号不得重置");
            }
            u.setPassword(new BCryptPasswordEncoder().encode("123456"));
            userService.update(u);
            redisTemplate.delete("user::" + u.getUsername());
        }
        return Response.yes("操作成功");
    }

    /**
     * 修改用户自己资料
     * 用户名密码不会修改 需要username更新缓存
     *
     * @param u
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @CacheEvict(key = "#u.username")
    public Response<Object> editOwn(@ModelAttribute User u) {

        User old = securityUtil.getCurrUser();
        u.setUsername(old.getUsername());
        u.setPassword(old.getPassword());
        User user = userService.update(u);
        if (user == null) {
            return Response.no("修改失败");
        }
        return Response.yes("修改成功");
    }

    /**
     * 管理员修改资料
     * 需要通过id获取原用户信息 需要username更新缓存
     *
     * @param u
     * @param roles
     * @return
     */
    @RequestMapping(value = "/admin/edit", method = RequestMethod.POST)
    @CacheEvict(key = "#u.username")
    public Response<Object> edit(@ModelAttribute User u,
                                 @RequestParam(required = false) String[] roles) {

        User old = userService.findById(u.getId());
        // 若修改了用户名
        if (!old.getUsername().equals(u.getUsername())) {
            // 若修改用户名删除原用户名缓存
            redisTemplate.delete("user::" + old.getUsername());
            // 判断新用户名是否存在
            if (userService.findByUsername(u.getUsername()) != null) {
                return Response.no("该用户名已存在");
            }
        }

        // 若修改了手机和邮箱判断是否唯一
        if (!old.getMobile().equals(u.getMobile()) && userService.findByMobile(u.getMobile()) != null) {
            return Response.no("该手机号已绑定其他账户");
        }
        if (!old.getEmail().equals(u.getEmail()) && userService.findByMobile(u.getEmail()) != null) {
            return Response.no("该邮箱已绑定其他账户");
        }

        u.setPassword(old.getPassword());
        User user = userService.update(u);
        if (user == null) {
            return Response.no("修改失败");
        }
        //删除该用户角色
        userRoleService.deleteByUserId(u.getId());
        if (roles != null && roles.length > 0) {
            //新角色
            for (String roleId : roles) {
                UserRole ur = new UserRole();
                ur.setRoleId(roleId);
                ur.setUserId(u.getId());
                userRoleService.insert(ur);
            }
        }
        //手动删除缓存
        redisTemplate.delete("userRole::" + u.getId());
        redisTemplate.delete("userRole::depIds:" + u.getId());
        redisTemplate.delete("permission::userMenuList:" + u.getId());
        return Response.yes("修改成功");
    }

    /**
     * 修改密码
     * 线上demo不允许测试账号改密码
     *
     * @param password
     * @param newPass
     * @return
     */
    @RequestMapping(value = "/modifyPass", method = RequestMethod.POST)
    public Response<Object> modifyPass(@RequestParam String password,
                                       @RequestParam String newPass) {

        User user = securityUtil.getCurrUser();
        //在线DEMO所需
        if ("test".equals(user.getUsername()) || "test2".equals(user.getUsername())) {
            return Response.no("演示账号不支持修改密码");
        }

        if (!new BCryptPasswordEncoder().matches(password, user.getPassword())) {
            return Response.no("旧密码不正确");
        }

        String newEncryptPass = new BCryptPasswordEncoder().encode(newPass);
        user.setPassword(newEncryptPass);
        userService.update(user);

        //手动更新缓存
        redisTemplate.delete("user::" + user.getUsername());

        return Response.yes("修改密码成功");
    }

    /**
     * 多条件分页获取用户列表
     *
     * @param user
     * @param searchVo
     * @param pageVo
     * @return
     */
    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    public Response<Page<User>> getByCondition(@ModelAttribute User user,
                                               @ModelAttribute SearchVo searchVo,
                                               @ModelAttribute PageVo pageVo) {

        Page<User> page = userService.findByCondition(new QueryCondition<User>(user, searchVo, PageUtil.initMpPage(pageVo)));
        for (User u : page.getRecords()) {
            // 关联角色
            List<Role> list = userRoleService.findByUserId(u.getId());
            u.setRoles(list);
            u.setPassword(null);
        }
        return Response.yes(page);
    }


    /**
     * 添加用户
     * @param u
     * @param roles
     * @return
     */
    @RequestMapping(value = "/admin/add", method = RequestMethod.POST)
    public Response<Object> regist(@ModelAttribute User u,
                                   @RequestParam(required = false) String[] roles) {

        if (StrUtil.isBlank(u.getUsername()) || StrUtil.isBlank(u.getPassword())) {
            return Response.no("缺少必需表单字段");
        }

        if (userService.findByUsername(u.getUsername()) != null) {
            return Response.no("该用户名已被注册");
        }

        String encryptPass = new BCryptPasswordEncoder().encode(u.getPassword());
        u.setPassword(encryptPass);
        User user = userService.insert(u);
        if (user == null) {
            return Response.no("添加失败");
        }
        if (roles != null && roles.length > 0) {
            //添加角色
            for (String roleId : roles) {
                UserRole ur = new UserRole();
                ur.setUserId(u.getId());
                ur.setRoleId(roleId);
                userRoleService.insert(ur);
            }
        }
        // 发送创建账号消息

        return Response.yes("添加成功");
    }

    /**
     * 后台禁用用户
     * @param userId
     * @return
     */
    @RequestMapping(value = "/admin/disable/{userId}", method = RequestMethod.POST)
    public Response<Object> disable(@PathVariable String userId) {

        User user = userService.findById(userId);
        if (user == null) {
            return Response.no("通过userId获取用户失败");
        }
        user.setStatus(CommonConstant.USER_STATUS_LOCK);
        userService.update(user);
        //手动更新缓存
        redisTemplate.delete("user::" + user.getUsername());
        return Response.yes("操作成功");
    }

    /**
     * 后台启用用户
     * @param userId
     * @return
     */
    @RequestMapping(value = "/admin/enable/{userId}", method = RequestMethod.POST)
    public Response<Object> enable(@PathVariable String userId) {

        User user = userService.findById(userId);
        if (user == null) {
            return Response.no("通过userId获取用户失败");
        }
        user.setStatus(CommonConstant.USER_STATUS_NORMAL);
        userService.update(user);
        //手动更新缓存
        redisTemplate.delete("user::" + user.getUsername());
        return Response.yes("操作成功");
    }

    /**
     * 批量通过ids删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delByIds/{ids}", method = RequestMethod.DELETE)
    public Response<Object> delAllByIds(@PathVariable String[] ids) {

        for (String id : ids) {
            User u = userService.findById(id);
            //删除相关缓存
            redisTemplate.delete("user::" + u.getUsername());
            redisTemplate.delete("userRole::" + u.getId());
            redisTemplate.delete("userRole::depIds:" + u.getId());
            redisTemplate.delete("permission::userMenuList:" + u.getId());
            Set<String> keys = redisTemplate.keys("department::*");
            redisTemplate.delete(keys);

            // 删除关联社交账号
//            qqService.deleteByUsername(u.getUsername());
//            weiboService.deleteByUsername(u.getUsername());
//            githubService.deleteByUsername(u.getUsername());

            userService.deleteById(id);

            //删除关联角色
            userRoleService.deleteByUserId(id);
        }
        return Response.yes("批量通过id删除数据成功");
    }

    /**
     * 导入用户数据
     * @param users
     * @return
     */
    @RequestMapping(value = "/importData", method = RequestMethod.POST)
    public Response<Object> importData(@RequestBody List<User> users) {

        List<Integer> errors = new ArrayList<>();
        List<String> reasons = new ArrayList<>();
        int count = 0;
        for (User u : users) {
            count++;
            // 验证用户名密码不为空
            if (StrUtil.isBlank(u.getUsername()) || StrUtil.isBlank(u.getPassword())) {
                errors.add(count);
                reasons.add("用户名或密码为空");
                continue;
            }
            // 验证用户名唯一
            if (userService.findByUsername(u.getUsername()) != null) {
                errors.add(count);
                reasons.add("用户名已存在");
                continue;
            }
            // 加密密码
            u.setPassword(new BCryptPasswordEncoder().encode(u.getPassword()));
            if (u.getStatus() == null) {
                u.setStatus(CommonConstant.USER_STATUS_NORMAL);
            }
            // 分配默认角色
          
            // 保存数据
            userService.insert(u);
        }
        int successCount = users.size() - errors.size();
        String successMessage = "全部导入成功，共计 " + successCount + " 条数据";
        String failMessage = "导入成功 " + successCount + " 条，失败 " + errors.size() + " 条数据。<br>" +
                "第 " + errors.toString() + " 行数据导入出错，错误原因分别为：<br>" + reasons.toString();
        String message = "";
        if (errors.size() == 0) {
            message = successMessage;
        } else {
            message = failMessage;
        }
        return Response.yes(message);
    }

    /**
     * 解锁验证密码
     * @param password
     * @return
     */
    @RequestMapping(value = "/unlock", method = RequestMethod.POST)
    public Response<Object> unLock(@RequestParam String password){

        User u = securityUtil.getCurrUser();
        if(!new BCryptPasswordEncoder().matches(password, u.getPassword())){
            return Response.no("密码不正确");
        }
        return Response.yes();
    }

}
