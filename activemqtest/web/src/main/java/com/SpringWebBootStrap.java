package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;

/**
 * @author  豪
 */

@SpringBootApplication
@MapperScan(basePackages = {"com.dao"})
@Configuration
public class SpringWebBootStrap extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(SpringWebBootStrap.class,args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SpringWebBootStrap.class);
    }
}
