package com.world.cloudxsolution;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class SecurityUtilsTest {

    @Test
    public void testEncryptionNotNull() {
        String testData = "{\"test\": \"data\"}";
        String encrypted = SecurityUtils.encrypt(testData);
        assertNotNull(encrypted);
        assertNotEquals(testData, encrypted);
    }
}
