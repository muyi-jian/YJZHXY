package com.yj.zhxy.pojo;

import lombok.Data;

/**
 * @author YangJian
 * @date 2023/12/25 10:05
 * @description
 */
@Data
public class LoginForm {

    private String username;
    private String password;
    private String verifiCode;
    private Integer userType;

}
