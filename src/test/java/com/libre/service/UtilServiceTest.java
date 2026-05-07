package com.libre.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

public class UtilServiceTest {
    @Test
    void testUtil() {
        for (int i = 0; i < 10; i++) {
            System.out.println(RandomStringUtils.randomNumeric(6,6));
        }
    }
}
