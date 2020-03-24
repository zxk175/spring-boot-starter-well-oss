package com.zxk175.oss.config.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author zxk175
 * @since 2020-03-23 17:31
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class AliOssProperties extends BaseOssProperties {

    /**
     * 访问域名
     */
    private String endPoint;

    /**
     * 请求超时（毫秒）
     */
    private Integer timeOut;

}
