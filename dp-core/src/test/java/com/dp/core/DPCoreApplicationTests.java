package com.dp.core;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.sql.init.mode=never",
        "spring.datasource.initialization-mode=never"  // Spring Boot 2.x 的旧配置
})
class DPCoreApplicationTests {

    @Test
    void contextLoads() {
    }

}
