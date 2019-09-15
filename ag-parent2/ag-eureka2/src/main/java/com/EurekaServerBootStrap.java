package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author  邱
 * @date 2019-9-6
 */

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerBootStrap {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerBootStrap.class,args);
    }

}
