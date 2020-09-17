package com.changgou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @auther wkk
 * @date 2020/6/1 15:13
 */
@SpringBootApplication
@EnableEurekaServer
public class EurakeApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurakeApplication.class,args);
    }

}
