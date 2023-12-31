package com.yj.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yj.zhxy.mapper.ClazzMapper;
import com.yj.zhxy.pojo.Clazz;
import com.yj.zhxy.service.ClazzService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author YangJian
 * @date 2023/12/31 14:18
 * @description
 */
@Service
@Transactional
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements ClazzService {

    @Override
    public IPage<Clazz> getClazzsByOpr(Page<Clazz> pageParam, Clazz clazz) {
        QueryWrapper<Clazz> queryWrapper=new QueryWrapper<>();
        String gradeName = clazz.getGradeName();
        if (StringUtils.isNotBlank(gradeName)) {
            queryWrapper.like("grade_name",gradeName);
        }

        String name = clazz.getName();
        if (StringUtils.isNotBlank(name)) {
            queryWrapper.like("name",name);
        }
        queryWrapper.orderByDesc("id");

        return baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    public List<Clazz> getClazzs() {
        return baseMapper.selectList(null);
    }
}
