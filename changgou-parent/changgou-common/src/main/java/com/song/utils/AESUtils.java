/**
 * flyiu.com Inc.
 * Copyright (c) 2018-2019 All Rights Reserved.
 */
package com.song.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

@Slf4j
public class AESUtils {
    
    private static String key = "12331fdfsdfsdfgtrttetert";

    /**
     * 加密
     * 
     * @param input
     * @return
     */
    public static String encrypt(String input) {
        byte[] crypted = null;
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skey);
            crypted = cipher.doFinal(input.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(Base64.encodeBase64(crypted));
    }

    public static String decrypt(String input) {
        byte[] output = null;
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skey);
            output = cipher.doFinal(Base64.decodeBase64(input));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return new String(output);
    }


    public static void main(String[] args) {
        String content = "你好，我是松哥";
        //String password = "xxxx";//密码就是key
        log.info("加密之前：" + content);
        // 加密
        String encrypt = AESUtils.encrypt(content);
        log.info("加密后的内容：" + encrypt);

        // 解密
        String decrypt = AESUtils.decrypt(encrypt);
        log.info("解密后的内容：" + decrypt);
    }

}
