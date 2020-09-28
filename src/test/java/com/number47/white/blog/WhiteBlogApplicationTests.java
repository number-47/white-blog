package com.number47.white.blog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WhiteBlogApplicationTests {

    @Test
    void contextLoads() {
        Float f = new Float(3.00);
        Integer i = 3;
        Float w = Float.valueOf(4);
        System.out.println(w);
        System.out.println(f);
    }

}
