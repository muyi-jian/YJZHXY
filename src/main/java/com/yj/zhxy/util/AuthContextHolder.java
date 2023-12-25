package com.yj.zhxy.util;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author YangJian
 * @date 2023/12/25 15:08
 * @description
 */
public class AuthContextHolder {
    //从请求头token获取userid
    public static Long getUserIdToken(HttpServletRequest request) {
        //从请求头token
        String token = request.getHeader("token");
        //调用工具类
        return JwtHelper.getUserId(token);
    }

    //从请求头token获取name
    public static String getUserName(HttpServletRequest request) {
        //从header获取token
        String token = request.getHeader("token");
        //jwt从token获取username
        return JwtHelper.getUserName(token);
    }
}
