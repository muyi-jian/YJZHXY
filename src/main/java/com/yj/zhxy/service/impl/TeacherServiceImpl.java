package com.yj.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yj.zhxy.mapper.TeacherMapper;
import com.yj.zhxy.pojo.LoginForm;
import com.yj.zhxy.pojo.Teacher;
import com.yj.zhxy.service.TeacherService;
import com.yj.zhxy.util.MD5;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author YangJian
 * @date 2023/12/25 16:31
 * @description
 */
@Service("teacherServiceImpl")
@Transactional
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    @Override
    public Teacher login(LoginForm loginForm) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", loginForm.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public Teacher getTeacherById(Long userId) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<Teacher> getTeachersByOpr(Page<Teacher> paraParam, Teacher teacher) {
        QueryWrapper<Teacher> queryWrapper =new QueryWrapper();
        if (StringUtils.isNotBlank(teacher.getName())){
            queryWrapper.like("name",teacher.getName());
        }
        if (StringUtils.isNotBlank(teacher.getClazzName())){
            queryWrapper.eq("clazz_name",teacher.getClazzName());
        }
        queryWrapper.orderByDesc("id");

        return baseMapper.selectPage(paraParam, queryWrapper);
    }
}
