package com.dhx.gulimall.gateway.config;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * @author dhx_
 * @className CrossConfigConfiguration
 * @date : 2022/12/28/ 00:03
 **/
@Configuration
public class GulimallCrossConfigConfiguration {

    @Bean
    public CorsWebFilter corsWebFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //1.配置跨域
//        corsConfiguration.addAllowedHeader("*"); //请求头
//        corsConfiguration.addAllowedMethod("*"); //请求方法 get/post/......
//        corsConfiguration.addAllowedOrigin("*"); //请求来源
        //这里使用addAllowedOrigin() 有一点问题 https://www.cnblogs.com/kanie-life/p/14285217.html
//        corsConfiguration.setAllowCredentials(true); //是否允许携带cookie
        //允许所有域名进行跨域调用
        corsConfiguration.addAllowedOriginPattern("*");
        //允许跨越发送cookie
        corsConfiguration.setAllowCredentials(true);
        //放行全部原始头信息
        corsConfiguration.addAllowedHeader("*");
        //允许所有请求方法跨域调用
        corsConfiguration.addAllowedMethod("*");

        source.registerCorsConfiguration("/**",corsConfiguration);

        return new CorsWebFilter(source);
    }
}
