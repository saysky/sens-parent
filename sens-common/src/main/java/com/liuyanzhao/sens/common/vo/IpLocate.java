package com.liuyanzhao.sens.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liuyanzhaox
 */
@Data
public class IpLocate implements Serializable {

    private String retCode;

    private City result;
}

