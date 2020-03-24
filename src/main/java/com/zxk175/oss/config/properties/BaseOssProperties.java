package com.zxk175.oss.config.properties;

import lombok.Data;

/**
 * @author zxk175
 * @since 2020-03-23 17:31
 */
@Data
public class BaseOssProperties {

    /**
     * accessKey
     */
    protected String accessKey;

    /**
     * accessSecret
     */
    protected String accessSecret;

    /**
     * 文件访问域名
     */
    protected String accessDomain;

    /**
     * 存储空间名
     */
    protected String bucketName;

}
