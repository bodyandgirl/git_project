package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@RequestMapping("com.mapper")
public class LoginBootStrap {
    public static void main(String[] args){
        SpringApplication.run(LoginBootStrap.class,args);
    }
}
