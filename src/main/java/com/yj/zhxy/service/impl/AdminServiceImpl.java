package com.yj.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yj.zhxy.mapper.AdminMapper;
import com.yj.zhxy.pojo.Admin;
import com.yj.zhxy.pojo.LoginForm;
import com.yj.zhxy.service.AdminService;
import com.yj.zhxy.util.MD5Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author YangJian
 * @date 2023/12/25 16:31
 * @description
 */
@Service("adminServiceImpl")
@Transactional
public class AdminServiceImpl extends ServiceImpl<AdminMapper,Admin> implements AdminService  {
    //@Override
    //public Admin login(LoginForm loginForm) {
    //    QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
    //    queryWrapper.eq("name", loginForm.getUsername());
    //    queryWrapper.eq("password", MD5Util.encrypt(loginForm.getPassword()));
    //    return baseMapper.selectOne(queryWrapper);
    //}
    //
    //@Override
    //public Admin getAdminById(Long userId) {
    //    QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
    //    queryWrapper.eq("id", userId);
    //    return baseMapper.selectOne(queryWrapper);
    //}
    //
    //@Override
    //public IPage<Admin> getAdminsByOpr(Page<Admin> pageParam, String adminName) {
    //    QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
    //    if(!StringUtils.hasText(adminName)){
    //        queryWrapper.like("name", adminName);
    //    }
    //    queryWrapper.orderByDesc("id");
    //    return baseMapper.selectPage(pageParam, queryWrapper);
    //}
}
