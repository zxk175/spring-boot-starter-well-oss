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
public class TxOssProperties extends BaseOssProperties {

    /**
     * bucket区域
     */
    private String region;

}
