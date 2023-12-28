package com.yj.zhxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yj.zhxy.pojo.Admin;
import com.yj.zhxy.pojo.LoginForm;
import com.yj.zhxy.pojo.Teacher;
import com.yj.zhxy.service.impl.TeacherServiceImpl;

/**
 * @author YangJian
 * @date 2023/12/25 16:27
 * @description
 */
public interface TeacherService extends IService<Teacher> {
    Teacher login(LoginForm loginForm);

    Teacher getTeacherById(Long userId);

}
