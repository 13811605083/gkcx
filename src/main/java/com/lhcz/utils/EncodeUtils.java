package com.lhcz.utils;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;

/**
 * @author seifur
 */
@Slf4j
public class EncodeUtils {

    /**
     * MD5加密后转成16进制
     * @param pwd 密码
     * @return String
     */
    public static String encodePwd(String pwd) {
        String algorithm = "MD5";
        try {
            //MD5加密
            MessageDigest instance = MessageDigest.getInstance(algorithm);
            byte[] digest = instance.digest(pwd.getBytes());
            //16进制
            return toHex(digest);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 字节转成16进制
     * @param buffer 字节
     * @return String
     */
    private static String toHex(byte[] buffer) {
        StringBuilder sb = new StringBuilder(buffer.length * 2);
        for (byte b : buffer) {
            sb.append(Character.forDigit((b & 240) >> 4, 16));
            sb.append(Character.forDigit(b & 15, 16));
        }
        return sb.toString();
    }
}


