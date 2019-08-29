package com.liuyanzhao.sens.user.web.vo;

import lombok.Data;

import java.util.List;

/**
 * @author liuyanzhao
 */
@Data
public class MenuVo {

    /**
     * id
     */
    private String id;

    /**
     * 父id
     */
    private String parentId;

    /**
     * 菜单/权限名称
     */
    private String name;

    /**
     * 始终显示
     */
    private Boolean showAlways;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 类型 -1顶部菜单 0页面 1具体操作
     */
    private Integer type;

    /**
     * 菜单标题
     */
    private String title;

    /**
     *  页面路径/资源链接url
     */
    private String path;

    /**
     * 前端组件
     */
    private String component;

    /**
     * 图标
     */
    private String icon;

    /**
     * 网页链接
     */
    private String url;

    /**
     * 按钮权限类型
     */
    private String buttonType;

    /**
     * 子菜单/权限
     */
    private List<MenuVo> children;

    /**
     * 页面拥有的权限类型
     */
    private List<String> permTypes;
}
