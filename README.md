# spring-boot-starter-well-oss

# 基于Gradle6.2.2

## 使用方法
### 1、pom文件直接添加依赖(该jar已经发布到maven中央仓库)
```text
implementation "com.zxk175:spring-boot-starter-well-oss:1.0.0"
```

### 2、在配置文件添加以下配置(只配 tx or ali)
```yaml
oss:
  tx:
    access-key: xxx
    access-secret: xxx
    access-domain: xxx
    bucket-name: xxx
    region: ap-guangzhou
  ali:
    access-key: xxx
    access-secret: xxx
    access-domain: xxx
    end-point: https://oss-cn-shenzhen.aliyuncs.com
    bucket-name: xxx
    time-out: 300000
```

### 3、使用代码

```java
@Controller
@AllArgsConstructor
@RequestMapping("/file")
public class FileController {

    private BaseOssService ossService;


    @ResponseBody
    @PostMapping("/upload/v1")
    public Response<Object> upload(MultipartFile file) throws Exception {
        System.out.println(ossService.getOssName());
        System.out.println(ossService.getProperties().toString());
        OssModel ossModel = ossService.uploadSuffix(file.getInputStream(), "oss/test/", "png");
        return Response.success(ossModel);
    }

}
```