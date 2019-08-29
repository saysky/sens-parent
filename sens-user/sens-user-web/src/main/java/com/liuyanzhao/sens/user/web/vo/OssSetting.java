package com.liuyanzhao.sens.user.web.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liuyanzhao
 */
@Data
public class OssSetting implements Serializable{

    /**
     * 服务商
     */
    private String serviceName;

    /**
     * ak
     */
    private String accessKey;

    /**
     * sk
     */
    private String secretKey;

    /**
     * endpoint域名
     */
    private String endpoint;

    /**
     * bucket空间
     */
    private String bucket;

    /**
     * http
     */
    private String http;

    /**
     * zone存储区域
     */
    private Integer zone;

    /**
     * bucket存储区域
     */
    private String bucketRegion;

    /**
     * 本地存储路径
     */
    private String filePath;

    /**
     * 是否改变secretKey
     */
    private Boolean changed;
}
