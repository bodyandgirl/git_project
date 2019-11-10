package com.hostqyh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author  豪
 *///exclude = {DataSourceAutoConfiguration.class}
@SpringBootApplication
@MapperScan(basePackages = {"com.hostqyh.dao"})
public class SpringbootBootClass {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootBootClass.class,args);
    }
}
