package com.yl.quartz.demo.domain.vo;

import lombok.Data;

/**
 * 公共请求
 * 
 * @author li.yang01@hand-china.com 2021/1/15 11:46 上午
 **/
@Data
public class BaseRequestVO {

    /**
     * 请求服务器地址（固定值）
     */
    protected String url;

    /**
     * 请求认证token（固定值）
     */
    protected String accessToken;

    /**
     * 应用appKey（固定值）
     */
    protected String appKey;

    /**
     * 应用secret（固定值）
     */
    protected String secret;

    /**
     * 应用sessionKey（固定值）
     */
    protected String sessionKey;

    /**
     * 超时时间
     */
    protected String timeOut;

}
