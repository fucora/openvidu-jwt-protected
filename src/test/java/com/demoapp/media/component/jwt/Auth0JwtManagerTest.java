package com.demoapp.media.component.jwt;

import org.junit.Test;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

import static org.junit.Assert.*;

public class Auth0JwtManagerTest {

    /**
     * Private RSA key.
     */
    private static final String PRIVATE_KEY = "MIICXAIBAAKBgQCKkz7AytCiZ61rqAq5St1ZDT/dbu1JtSz+f1eBp4YF5M2VSy6tnbF7ZwcME+n98RM5B6/40NAHgdgz6FvB15HuTxeOtIeaqaDxxTdGPoWnZaKLRSZ7v8JOs6DMRKX+0/gCbTPMW3o6m+ZJFzJ77Q7nkgTJgIXRoC8cOw2tgv1HvQIDAQABAoGAQeHbdeF265+LDaHo50s2jT4FXQt9+QsQBzcXrWauC2NKcm46FQTh+p9y9EULfFX/I6AKA6uTG96ZoaQY68HHeOGk8Ku5SFfQbSfboEtp+55lEDG0iDjMNAkPhFs1BteI8dhLSua7s5B8TFd6SWVIABAnNrnUbPhbhGiag7U8K8ECQQD23ikBlqm/q5CxrqgR7okNEpRj/NhWQMrpdr+aaedEdKWRH+6/Qx/BqjpUEZ9h7GiuARIYisEfxxia5q5/GztJAkEAj7ONjviZFxu9p2srd7eDw3MJ3nprpWo09AVtLsy+76klV0c5ND4ee4T+gy9Ql614O/ukErhXqe1Kdu6uCdlU1QJBAMyiSVGExz8Xg0CWK8F5fme0sAbWD6on2Ut/YMIp6UeofZn341rt1EQfzGvlk72DTx51yr9HPWEPE4lLkPfR5ekCQEzupaSbNuqGMXzGBVlsaCBlIC15gNPoDj1uAkwB1V7dQV5+hQORFiSuyMtWs2Dgv/Ps/qP/CP+ySCFwGsCvF5kCQEDVz47m/VQ4+mGdqK7uyywDy4Yg7v/3whhEseg00jw+pE3xoCJlTYbUXli6ovaOj58S9rJJ8onF/U6FiUF88Ow=";
    /**
     * Public RSA key.
     */
    private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCKkz7AytCiZ61rqAq5St1ZDT/dbu1JtSz+f1eBp4YF5M2VSy6tnbF7ZwcME+n98RM5B6/40NAHgdgz6FvB15HuTxeOtIeaqaDxxTdGPoWnZaKLRSZ7v8JOs6DMRKX+0/gCbTPMW3o6m+ZJFzJ77Q7nkgTJgIXRoC8cOw2tgv1HvQIDAQAB";
    /**
     * Private RSA key converted to PKCS8 syntax.
     */
    private static final String PRIVATE_KEY_PKCS8 = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIqTPsDK0KJnrWuoCrlK3VkNP91u7Um1LP5/V4GnhgXkzZVLLq2dsXtnBwwT6f3xEzkHr/jQ0AeB2DPoW8HXke5PF460h5qpoPHFN0Y+hadlootFJnu/wk6zoMxEpf7T+AJtM8xbejqb5kkXMnvtDueSBMmAhdGgLxw7Da2C/Ue9AgMBAAECgYBB4dt14Xbrn4sNoejnSzaNPgVdC335CxAHNxetZq4LY0pybjoVBOH6n3L0RQt8Vf8joAoDq5Mb3pmhpBjrwcd44aTwq7lIV9BtJ9ugS2n7nmUQMbSIOMw0CQ+EWzUG14jx2EtK5ruzkHxMV3pJZUgAECc2udRs+FuEaJqDtTwrwQJBAPbeKQGWqb+rkLGuqBHuiQ0SlGP82FZAyul2v5pp50R0pZEf7r9DH8GqOlQRn2HsaK4BEhiKwR/HGJrmrn8bO0kCQQCPs42O+JkXG72nayt3t4PDcwneemulajT0BW0uzL7vqSVXRzk0Ph57hP6DL1CXrXg7+6QSuFep7Up27q4J2VTVAkEAzKJJUYTHPxeDQJYrwXl+Z7SwBtYPqifZS39gwinpR6h9mffjWu3URB/Ma+WTvYNPHnXKv0c9YQ8TiUuQ99Hl6QJATO6lpJs26oYxfMYFWWxoIGUgLXmA0+gOPW4CTAHVXt1BXn6FA5EWJK7Iy1azYOC/8+z+o/8I/7JIIXAawK8XmQJAQNXPjub9VDj6YZ2oru7LLAPLhiDu//fCGESx6DTSPD6kTfGgImVNhtReWLqi9o6PnxL2sknyicX9ToWJQXzw7A==";

    /**
     * A token signed using the RSA private key above.
     */
    private static final String SIGNED_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOjF9.EGsplbhKSR7CGN5v2MuztHlPM2KYm3G6kjYUjD7NmFSQS1CYngRDJBVyemTZ6psr3kt2hKSsgjgXHgnwqI_CidcZkV484eTLfgA248eu_QEYEd6FVZNvAQ9UakmnGZjzW1MJIN77MWwSyvCfNWsI2Dz8oIjp6hm16VcfDmcyCTg";

    @Test
    public void encode() throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(
                new PKCS8EncodedKeySpec(Base64.getDecoder().decode(PRIVATE_KEY_PKCS8)));

        Map<String, Object> payload = new HashMap<>();
        payload.put("sub", 1);

        JwtManager jwtManager = new Auth0JwtManager((RSAPrivateKey) privateKey);
        String token = jwtManager.encode(payload);

        assertNotNull(token);

        Map<String, Object> decodedPayload = jwtManager.decode(token);

        assertFalse(decodedPayload.isEmpty());
        assertEquals(1, decodedPayload.get("sub"));
    }

    @Test
    public void decode() throws Exception {
        JwtManager jwtManager = new Auth0JwtManager();
        Map<String, Object> decodedPayload = jwtManager.decode(SIGNED_TOKEN);

        assertFalse(decodedPayload.isEmpty());
        assertEquals(1, decodedPayload.get("sub"));
    }

    @Test
    public void verify() throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(
                new X509EncodedKeySpec(Base64.getDecoder().decode(PUBLIC_KEY)));

        JwtManager jwtManager = new Auth0JwtManager((RSAPublicKey) publicKey);

        assertTrue(jwtManager.verify(SIGNED_TOKEN));
    }
}