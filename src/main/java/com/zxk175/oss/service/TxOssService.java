package com.zxk175.oss.service;

import cn.hutool.core.util.StrUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import com.zxk175.oss.config.properties.OssProperties;
import com.zxk175.oss.config.properties.TxOssProperties;
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
public class TxOssService extends BaseOssService {

    private COSClient cosClient;
    private TxOssProperties txOssProperties;


    TxOssService(TxOssProperties txOssProperties) {
        this.ossProperties = new OssProperties();
        this.ossProperties.setTx(txOssProperties);
        this.txOssProperties = txOssProperties;

        COSCredentials cosCredentials = new BasicCOSCredentials(txOssProperties.getAccessKey(), txOssProperties.getAccessSecret());
        ClientConfig clientConfig = new ClientConfig(new Region(txOssProperties.getRegion()));
        cosClient = new COSClient(cosCredentials, clientConfig);
    }

    @Override
    public OssModel upload(byte[] data, String path) {
        return upload(new ByteArrayInputStream(data), path);
    }

    @Override
    public OssModel upload(InputStream inputStream, String path) {
        try {
            // 腾讯云必需要以"/"开头
            if (!path.startsWith(File.separator)) {
                path = File.separator + path;
            }

            long fileSize = inputStream.available();

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(fileSize);
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentEncoding(StandardCharsets.UTF_8.name());

            PutObjectRequest request = new PutObjectRequest(txOssProperties.getBucketName(), path, inputStream, objectMetadata);
            PutObjectResult result = cosClient.putObject(request);
            if (StrUtil.isEmpty(result.getETag())) {
                throw new OssException("文件上传失败.");
            }

            return new OssModel(path, txOssProperties.getAccessDomain() + path);
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
