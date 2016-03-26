package com.vonzhou.mvnbook.account.captcha;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertFalse;

/**
 * Created by vonzhou on 16/3/25.
 */
public class RandomGeneratorTest {
    @Test
    public void testGetRandomString() throws Exception{
        Set<String> randoms = new HashSet<String>(100);
        for(int i=0; i<100; i++){
            String random = RandomGenerator.getRandomString();
            assertFalse(randoms.contains(random));
            randoms.add(random);
        }
    }
}
