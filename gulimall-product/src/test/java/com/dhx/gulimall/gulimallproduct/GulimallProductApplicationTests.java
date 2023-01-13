package com.dhx.gulimall.gulimallproduct;

import com.aliyun.oss.*;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dhx.gulimall.product.GulimallProductApplication;
import com.dhx.gulimall.product.entity.BrandEntity;
import com.dhx.gulimall.product.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.util.List;

@SpringBootTest(classes = GulimallProductApplication.class)
class GulimallProductApplicationTests {

    @Test
    void contextLoads() {
    }
    @Resource
    BrandService brandService;

    @Test
    public void insertTest(){
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setName("华为");
        brandService.save(brandEntity);
        //13
        System.out.println(brandEntity.getBrandId());
    }
    @Test
    public void updateTest(){
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setDescript("华为品牌");
        brandEntity.setBrandId(13L);
        brandService.updateById(brandEntity);
    }
    @Test
    public void selectTest(){
        List<BrandEntity> list = brandService.list(new QueryWrapper<BrandEntity>().eq("name", "华为"));
        for (BrandEntity brandEntity : list) {
            System.out.println(brandEntity.getBrandId());
        }
    }
//    @Resource
//    OSSClient ossClient;

//    @Test
//    public void uploadTest(){
//        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
//        String objectName = "test/exampleobject.txt";
//        // 创建OSSClient实例。
//        try {
//            // 填写字符串。
//            String content = "Hello OSS，你好世界";
//            // 创建PutObjectRequest对象。
//            PutObjectRequest putObjectRequest = new PutObjectRequest( "20230112-gulimall", objectName, new ByteArrayInputStream(content.getBytes()));
//            // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
//            // ObjectMetadata metadata = new ObjectMetadata();
//            // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
//            // metadata.setObjectAcl(CannedAccessControlList.Private);
//            // putObjectRequest.setMetadata(metadata);
//
//            // 设置该属性可以返回response。如果不设置，则返回的response为空。
//            putObjectRequest.setProcess("true");
//            // 上传字符串。
//            PutObjectResult result = ossClient.putObject(putObjectRequest);
//            // 如果上传成功，则返回200。
//            System.out.println(result.getResponse().getStatusCode());
//        } catch (OSSException oe) {
//            System.out.println("Caught an OSSException, which means your request made it to OSS, "
//                    + "but was rejected with an error response for some reason.");
//            System.out.println("Error Message:" + oe.getErrorMessage());
//            System.out.println("Error Code:" + oe.getErrorCode());
//            System.out.println("Request ID:" + oe.getRequestId());
//            System.out.println("Host ID:" + oe.getHostId());
//        } catch (ClientException ce) {
//            System.out.println("Caught an ClientException, which means the client encountered "
//                    + "a serious internal problem while trying to communicate with OSS, "
//                    + "such as not being able to access the network.");
//            System.out.println("Error Message:" + ce.getMessage());
//        } finally {
//            if (ossClient != null) {
//                ossClient.shutdown();
//            }
//        }
//    }
}
