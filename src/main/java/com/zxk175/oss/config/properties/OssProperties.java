package com.zxk175.oss.config.properties;

import com.zxk175.oss.config.OssConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author zxk175
 * @since 2020-03-24 10:35
 */
@Data
@ConfigurationProperties(prefix = OssConstants.OSS_CONFIG_PREFIX)
public class OssProperties {

    /**
     * 阿里配置
     */
    @NestedConfigurationProperty
    private AliOssProperties ali;

    /**
     * 腾讯配置
     */
    @NestedConfigurationProperty
    private TxOssProperties tx;

}
