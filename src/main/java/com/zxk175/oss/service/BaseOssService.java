package com.zxk175.oss.service;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.zxk175.oss.config.properties.OssProperties;
import com.zxk175.oss.entity.OssModel;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

/**
 * @author zxk175
 * @since 2020-03-24 09:46
 */
@Slf4j
public abstract class BaseOssService {

    /**
     * 云存储配置信息
     */
    protected OssProperties ossProperties;


    /**
     * 文件路径 如果没有prefix，则直接返回当前时间 prefix/uuid, 如: oss/test/28282993.png
     *
     * @param prefix 前缀 oss/test/
     * @param suffix 后缀 png
     * @return 返回上传路径
     */
    public String getPath(String prefix, String suffix) {
        String path = IdUtil.fastSimpleUUID();
        if (StrUtil.isNotBlank(prefix)) {
            path = prefix + path;
        }

        return path + StrUtil.C_DOT + suffix;
    }

    /**
     * 文件上传
     *
     * @param data 文件字节数组
     * @param path 文件路径，包含文件名
     * @return 返回http地址
     */
    public abstract OssModel upload(byte[] data, String path);

    /**
     * 文件上传
     *
     * @param inputStream 字节流
     * @param path        文件路径，包含文件名
     * @return 返回http地址
     */
    public abstract OssModel upload(InputStream inputStream, String path);

    /**
     * 文件上传,不指定路径，按照日期生成
     *
     * @param data   文件字节数组
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 返回http地址
     */
    public abstract OssModel uploadSuffix(byte[] data, String prefix, String suffix);

    /**
     * 文件上传,不指定路径，按照日期生成
     *
     * @param inputStream 字节流
     * @param prefix      前缀
     * @param suffix      后缀
     * @return 返回http地址
     */
    public abstract OssModel uploadSuffix(InputStream inputStream, String prefix, String suffix);

    public String getOssName() {
        if (ObjectUtil.isNotNull(this.ossProperties.getTx())) {
            return "腾讯云";
        }

        if (ObjectUtil.isNotNull(this.ossProperties.getAli())) {
            return "阿里云";
        }

        throw new RuntimeException("OssType不存在");
    }

    public OssProperties getProperties() {
        return this.ossProperties;
    }

    public static class Builder {
        OssProperties ossProperties;

        public Builder(OssProperties ossProperties) {
            this.ossProperties = ossProperties;
        }

        public BaseOssService build() {
            if (ObjectUtil.isNotNull(this.ossProperties.getTx())) {
                return new TxOssService(ossProperties.getTx());
            }

            if (ObjectUtil.isNotNull(this.ossProperties.getAli())) {
                return new AliOssService(ossProperties.getAli());
            }

            throw new RuntimeException("OssType不存在");
        }
    }

}