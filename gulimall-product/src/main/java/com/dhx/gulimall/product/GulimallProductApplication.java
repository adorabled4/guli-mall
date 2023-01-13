package com.dhx.gulimall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@MapperScan("com.dhx.gulimall.product.dao")
@SpringBootApplication
@EnableDiscoveryClient
public class GulimallProductApplication {

    /// key LTAI5tSWB1RrfpVPBo68ayqw  secret CeEgJtVgiYEBu7dXb23DWbTmnvBB7K
    public static void main(String[] args) {
        SpringApplication.run(GulimallProductApplication.class, args);
    }

}
