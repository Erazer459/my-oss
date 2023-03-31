package io.github.franzli347.foss.utils;

import cn.dev33.satoken.stp.StpUtil;
import io.github.franzli347.foss.common.constant.AuthConstant;

/**
 * @ClassName AuthUtil
 * @Author AlanC
 * @Date 2023/1/13 15:36
 **/
public class AuthUtil {
    public static String bidConcat(Object bid,Object privilege){
        return bid+"-"+privilege;
    }
    public static void privilegeCheck(int uid,String privilege){
        if (uid==StpUtil.getLoginIdAsInt()){
            throw new RuntimeException("无法为自己设置权限");
        }
        if (!privilege.equals(AuthConstant.READWRITE)&&!privilege.equals(AuthConstant.ONLYREAD)){
            throw new RuntimeException("权限类型错误,只能为r或rw");
        }
    }
}
