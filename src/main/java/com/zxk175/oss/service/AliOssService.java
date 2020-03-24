package com.zxk175.oss.service;

import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.zxk175.oss.config.properties.AliOssProperties;
import com.zxk175.oss.config.properties.OssProperties;
import com.zxk175.oss.entity.OssModel;
import com.zxk175.oss.exception.OssException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author zxk175
 * @since 2020-03-24 09:53
 */
public class AliOssService extends BaseOssService {

    private OSSClient ossClient;
    private AliOssProperties aliOssProperties;


    AliOssService(AliOssProperties aliOssProperties) {
        this.ossProperties = new OssProperties();
        this.ossProperties.setAli(aliOssProperties);
        this.aliOssProperties = aliOssProperties;

        ClientConfiguration clientConfiguration = new ClientConfiguration();
        // 设置失败请求重试次数，默认为3次
        clientConfiguration.setMaxErrorRetry(5);
        // 设置ossClient允许打开的最大HTTP连接数，默认为1024个
        clientConfiguration.setMaxConnections(600);
        // 设置socket层传输数据的超时时间，默认为50000毫秒
        clientConfiguration.setSocketTimeout(aliOssProperties.getTimeOut());
        // 设置建立连接的超时时间，默认为50000毫秒
        clientConfiguration.setConnectionTimeout(aliOssProperties.getTimeOut());
        // 设置从连接池中获取连接的超时时间（单位：毫秒），默认不超时
        clientConfiguration.setConnectionRequestTimeout(aliOssProperties.getTimeOut());
        // 设置连接空闲超时时间。超时则关闭连接，默认为60000毫秒
        clientConfiguration.setIdleConnectionTime(aliOssProperties.getTimeOut());

        DefaultCredentialProvider credentialProvider = new DefaultCredentialProvider(aliOssProperties.getAccessKey(), aliOssProperties.getAccessSecret());
        ossClient = new OSSClient(aliOssProperties.getEndPoint(), credentialProvider, clientConfiguration);
    }

    @Override
    public OssModel upload(byte[] data, String path) {
        return upload(new ByteArrayInputStream(data), path);
    }

    @Override
    public OssModel upload(InputStream inputStream, String path) {
        try {
            long fileSize = inputStream.available();

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(fileSize);
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentEncoding(StandardCharsets.UTF_8.name());

            PutObjectResult result = ossClient.putObject(aliOssProperties.getBucketName(), path, inputStream, objectMetadata);
            if (StrUtil.isEmpty(result.getETag())) {
                throw new OssException("文件上传失败.");
            }

            if (!path.startsWith(File.separator)) {
                path = File.separator + path;
            }
            return new OssModel(path, aliOssProperties.getAccessDomain() + path);
        } catch (Exception ex) {
            throw new OssException("上传文件失败，请检查配置", ex);
        }
    }

    @Override
    public OssModel uploadSuffix(byte[] data, String prefix, String suffix) {
        return upload(data, getPath(prefix, suffix));
    }

    @Override
    public OssModel uploadSuffix(InputStream inputStream, String prefix, String suffix) {
        return upload(inputStream, getPath(prefix, suffix));
    }

}
