package com.zxk175.oss;

import com.zxk175.oss.config.properties.OssProperties;
import com.zxk175.oss.service.BaseOssService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author zxk175
 * @since 2020-03-24 09:40
 */
@EnableConfigurationProperties({OssProperties.class})
public class OssAutoConfigure {

    @Bean
    @ConditionalOnMissingBean
    public BaseOssService baseOssService(OssProperties properties) {
        return new BaseOssService.Builder(properties).build();
    }

}