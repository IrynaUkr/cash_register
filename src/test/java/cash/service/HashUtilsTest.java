package cash.service;

import cash.HashUtils;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.Assert.*;

public class HashUtilsTest {
    String password = "password";

    @Test
    public void generateStrongPasswordHashNotSameTwoTimesCallTest() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String hash1 = HashUtils.generateStrongPasswordHash(password);
        String hash2 = HashUtils.generateStrongPasswordHash(password);
        assertNotSame(hash1, hash2);
    }

    @Test
    public void validatePasswordTheSamePasswordTest() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String generatedSecuredPasswordHash = HashUtils.generateStrongPasswordHash(password);
        assertTrue(HashUtils.validatePassword(password, generatedSecuredPasswordHash));
    }
    @Test
    public void validatePasswordTheDifferentPasswordsTest() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String generatedSecuredPasswordHash = HashUtils.generateStrongPasswordHash(password);
        String differentPassword ="differentPassword";
        assertFalse(HashUtils.validatePassword(differentPassword, generatedSecuredPasswordHash));
    }
    @Test
    public void toHexTest(){
        byte[] bytes = password.getBytes(StandardCharsets.UTF_8);
        String actual = HashUtils.toHex(bytes);
        String expected = "70617373776f7264";
        assertEquals(actual, expected);
    }

}
