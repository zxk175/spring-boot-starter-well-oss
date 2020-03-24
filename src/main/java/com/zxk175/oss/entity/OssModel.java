package com.zxk175.oss.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zxk175
 * @since 2020-03-24 09:44
 */
@Data
@NoArgsConstructor
public class OssModel {

    /**
     * 文件存储相对路径
     */
    private String fileUrl;

    /**
     * 文件可访问绝对路径
     */
    private String accessUrl;


    public OssModel(String fileUrl, String accessUrl) {
        this.fileUrl = fileUrl;
        this.accessUrl = accessUrl;
    }
    
}