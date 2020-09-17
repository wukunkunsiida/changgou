package com.changgou.oauth;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
import springfox.documentation.spring.web.json.Json;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

public class CreateJwtTest {

    @Test
    public void test1(){

        ClassPathResource resource=new ClassPathResource("changgou.jks");
        KeyStoreKeyFactory keyStoreKeyFactory=new KeyStoreKeyFactory(resource,"changgou".toCharArray());
        KeyPair keyPair=keyStoreKeyFactory.getKeyPair("changgou","changgou".toCharArray());
        RSAPrivateKey privateKey=(RSAPrivateKey)keyPair.getPrivate();
        Map payhah=new HashMap<>();
        payhah.put("nikename","wukunkun");
        payhah.put("role","wukunkun");
        payhah.put("authorities",new String[]{"admin","oauth"});
        Jwt jwt= JwtHelper.encode(JSON.toJSONString(payhah),new RsaSigner(privateKey));
        System.out.println(jwt.getEncoded());
    }

    @Test
    public void test(){
    String publicKey="-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvFsEiaLvij9C1Mz+oyAmt47whAaRkRu/8kePM+X8760UGU0RMwGti6Z9y3LQ0RvK6I0brXmbGB/RsN38PVnhcP8ZfxGUH26kX0RK+tlrxcrG+HkPYOH4XPAL8Q1lu1n9x3tLcIPxq8ZZtuIyKYEmoLKyMsvTviG5flTpDprT25unWgE4md1kthRWXOnfWHATVY7Y/r4obiOL1mS5bEa/iNKotQNnvIAKtjBM4RlIDWMa6dmz+lHtLtqDD2LF1qwoiSIHI75LQZ/CNYaHCfZSxtOydpNKq8eb1/PGiLNolD4La2zf0/1dlcr5mkesV570NxRmU1tFm8Zd3MZlZmyv9QIDAQAB-----END PUBLIC KEY-----";
     String token="eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJhcHAiXSwibmFtZSI6bnVsbCwiaWQiOm51bGwsImV4cCI6MjAyODk1MDc0NiwiYXV0aG9yaXRpZXMiOlsic2Vja2lsbF9saXN0IiwiZ29vZHNfbGlzdCJdLCJqdGkiOiIzNmZmNDI4MS1jZmVkLTRjZDctYWUwZC1hN2ZkZjk1MTRkNzMiLCJjbGllbnRfaWQiOiJjaGFuZ2dvdSIsInVzZXJuYW1lIjoic3ppdGhlaW1hIn0.IGT5LiiASx6_ZJoJYb5UK55yt6hF9yaCqc5_2HmVGPBDWHKja3aqXGTeZPgjiK-wMXzYSeLQTDlmStGHeKqTQYHsAa1RbZ57Wa-7lvSPD9uZs4aTIZ2N39CDIAnECGMNIAMJejmexMBSUASmLCN-uif407e0GrHI4UwoRAPAn8ceid9KMFqSzGCHF6f6NsPxE3sCYCs2FDxiRUsfA1qa8bfs5_F5QKLgf0i-fkdjtnu6IFIDcc4AAcxJWiXhkMG_Gss5nouIVnpYnekEjMxfWdBsVINlmwmk2sMAukm7ri9GEmk1Nm3OHLB3GFUV8_hMP85VfNCj5SRj5eC1Ee6N8Q";
    Jwt jwt=JwtHelper.decodeAndVerify(token,new RsaVerifier(publicKey));
        System.out.println(jwt.getClaims().toString());
    }
   
}
