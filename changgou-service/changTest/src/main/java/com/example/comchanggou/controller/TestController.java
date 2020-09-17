package com.example.comchanggou.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther wkk
 * @date 2020/9/1 16:24
 */

@RestController
@RefreshScope//nacos配置中心时添加上
public class TestController {

    @GetMapping(value = "/hello")
    public String hello() {
        return "Hello Sentinel";
    }

    @NacosValue(value = "${nacos.test.propertie:12388}", autoRefreshed = true)
    private String testProperties;
    @Value(value = "${nacos.test.propertie:12388}" )
    private String testProperties1;

    @GetMapping("/test")
    public String test(){
        return testProperties+"1111"+testProperties1;
    }
}