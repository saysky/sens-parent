package com.liuyanzhao.sens.user.web.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liuyanzhao
 */
@Data
public class VaptchaSetting implements Serializable{

    /**
     * vid
     */
    private String vid;

    /**
     * secretKey
     */
    private String secretKey;

    /**
     * 场景
     */
    private String scene;

    /**
     * 是否改变secretkey
     */
    private Boolean changed;
}
