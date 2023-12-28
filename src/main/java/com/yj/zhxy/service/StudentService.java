package com.yj.zhxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yj.zhxy.pojo.Admin;
import com.yj.zhxy.pojo.LoginForm;
import com.yj.zhxy.pojo.Student;

/**
 * @author YangJian
 * @date 2023/12/25 16:27
 * @description
 */
public interface StudentService extends IService<Student> {
    Student login(LoginForm loginForm);
    Student getStudentById(Long userId);
}
