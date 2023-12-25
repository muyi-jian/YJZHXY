package com.yj.zhxy.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import org.springframework.util.StringUtils;


import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

/**
 * @author YangJian
 * @date 2023/12/25 10:52
 * @description token口令生成工具
 */
public class JwtHelper {
    /**
     * 过期时间(单位:秒)
     */
    public final static int TOKEN_EXPIRE = 60;
    /**
     * jwt签发者
     */
    private final static String JWT_ISS = "YJ";
    /**
     * jwt主题
     */
    private final static String SUBJECT = "YJZHXY-USER";
    /**
     * 私钥 / 生成签名的时候使用的秘钥secret，一般可以从本地配置文件中读取，切记这个秘钥不能外露，只在服务端使用，在任何场景都不应该流露出去。
     * 一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
     * 应该大于等于 256位(长度32及以上的字符串)，并且是随机的字符串
     */
    private final static String SECRET = "123456";
    /**
     * 秘钥实例
     */
    public static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes());
    /**
     * 加密算法
     */
    private final static SecureDigestAlgorithm<SecretKey, SecretKey> ALGORITHM = Jwts.SIG.HS512;

    /*
    这些是一组预定义的声明，它们 不是强制性的，而是推荐的 ，以 提供一组有用的、可互操作的声明 。
    iss: jwt签发者
    sub: jwt所面向的用户
    aud: 接收jwt的一方
    exp: jwt的过期时间，这个过期时间必须要大于签发时间
    nbf: 定义在什么时间之前，该jwt都是不可用的.
    iat: jwt的签发时间
    jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击
     */

    //生成token字符串
    public static String createToken(Long userId, Integer userType) {
        Date exprireDate = Date.from(Instant.now().plusSeconds(TOKEN_EXPIRE));
        return Jwts.builder()
                // 设置头部信息header
                .header()
                .add("typ", "JWT")
                .add("alg", "HS512")
                .and()
                // 主题-即令牌所表示的用户或实体。
                .subject(SUBJECT)
                //过期日期
                .expiration(exprireDate)
                // 设置自定义负载信息payload
                .claim("userId", userId)
                //.claim("userName", userName)
                .claim("userType", userType)
                .issuedAt(new Date())
                .issuer(JWT_ISS)
                // 签名
                .signWith(KEY, ALGORITHM)
                // 压缩方式
                .compressWith(Jwts.ZIP.GZIP)
                .compact();
    }

    /**
     * 解析token
     * @param token token
     * @return Jws<Claims>
     */
    public static Jws<Claims> parseClaim(String token) {
        return Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token);
    }
    /**
     * 获取JwsHeader
     * @param token token
     * @return JwsHeader
     */
    public static JwsHeader parseHeader(String token) {
        if(StringUtils.hasText(token)) {
            return null;
        }
        return parseClaim(token).getHeader();
    }

    /**
     * 获取自定义负载信息
     * @param token token
     * @return Claims
     */
    public static Claims parsePayload(String token) {
        if(StringUtils.hasText(token)) {
            return null;
        }
        return parseClaim(token).getPayload();
    }


    //从token字符串获取userid
    public static Long getUserId(String token) {
        Claims claims = parsePayload(token);
        if (claims == null){
            return null;
        }
        Integer userId = (Integer)claims.get("userId");
        return userId.longValue();
    }

    //从token字符串获取userType
    public static Integer getUserType(String token) {
        Claims claims = parsePayload(token);
        if (claims == null){
            return null;
        }
        return (Integer)(claims.get("userType"));
    }

    //从token字符串获取userName
    public static String getUserName(String token) {
        Claims claims = parsePayload(token);
        if (claims == null){
            return null;
        }
        return (String)claims.get("userName");
    }



    //判断token是否有效
    public static boolean isExpiration(String token){
        try {
            Claims claims = parsePayload(token);
            if (claims == null){
                return true;
            }
            //没有过期，有效，返回false
            return  claims.getExpiration().before(new Date());
        }catch(Exception e) {
            //过期出现异常，返回true
            return true;
        }
    }


    /**
     * 刷新Token
     * @param token token
     * @return 最新token
     */
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            refreshedToken = JwtHelper.createToken(getUserId(token), getUserType(token));
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public static void main(String[] args) {
//        String token = JwtHelper.createToken(1L, "lucy");
//        System.out.println(token);
//        System.out.println(JwtHelper.getUserId(token));
//        System.out.println(JwtHelper.getUserName(token));
    }
}
