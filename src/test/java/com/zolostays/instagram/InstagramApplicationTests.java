package com.zolostays.instagram;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootTest
@EnableJpaAuditing
@EnableAsync
class InstagramApplicationTests {

    @Test
    void contextLoads() {
    }

}
