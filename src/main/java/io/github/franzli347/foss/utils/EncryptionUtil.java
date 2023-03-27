package io.github.franzli347.foss.utils;

import cn.dev33.satoken.secure.SaSecureUtil;
import lombok.extern.slf4j.Slf4j;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;



/**
 * @Author AlanC
 * @Description  密码salt加密工具类
 * @Date 16:46 2023/1/8
 **/
@Slf4j
public class EncryptionUtil {
    private static final String salt="turing_oss";
    /**
     * 验证输入的password是否正确
     * @param attemptedPassword 待验证的password
     * @param encryptedPassword 密文
     */
    public static boolean authenticate(String attemptedPassword, String encryptedPassword){
        // 用同样的盐值对用户输入的password进行加密
        String encryptedAttemptedPassword= getEncryptedPassword(attemptedPassword);
        // 把加密后的密文和原密文进行比較，同样则验证成功。否则失败
        return encryptedAttemptedPassword.equals(encryptedPassword);
    }
    /**
     * 生成密文
     * @param password 明文password
     */
    public static String getEncryptedPassword(String password){
        return SaSecureUtil.sha1(password+salt);
    }


}
