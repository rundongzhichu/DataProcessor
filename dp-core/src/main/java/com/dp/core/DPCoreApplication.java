package com.dp.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class  // 如果需要同时禁用数据源
})
public class DPCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(DPCoreApplication.class, args);
    }

}
