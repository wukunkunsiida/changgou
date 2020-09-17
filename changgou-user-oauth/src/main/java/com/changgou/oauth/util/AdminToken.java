package com.changgou.oauth.util;

import com.alibaba.fastjson.JSON;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther wkk
 * @date 2020/8/9 15:18
 */
public class AdminToken {
    public static String admintoken(){
        ClassPathResource resource=new ClassPathResource("changgou.jks");
        KeyStoreKeyFactory keyStoreKeyFactory=new KeyStoreKeyFactory(resource,"changgou".toCharArray());
        KeyPair keyPair=keyStoreKeyFactory.getKeyPair("changgou","changgou".toCharArray());
        RSAPrivateKey privateKey=(RSAPrivateKey)keyPair.getPrivate();
        Map payhah=new HashMap<>();
        payhah.put("nikename","wukunkun");
        payhah.put("role","wukunkun");
        payhah.put("authorities",new String[]{"admin","oauth"});
        Jwt jwt= JwtHelper.encode(JSON.toJSONString(payhah),new RsaSigner(privateKey));
        jwt.getEncoded();
        return jwt.getEncoded();
    }
}
