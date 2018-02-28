package com.imooc.miaosha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * extends SpringBootServletInitializer
 * @author yangqian
 * @date 2018/1/13
 */
@EnableTransactionManagement
@SpringBootApplication
public class MiaoShaApplication {

    @Bean(name = "txManager1")
    public PlatformTransactionManager txManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MiaoShaApplication.class, args);
    }
}
