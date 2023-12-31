package com.yj.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yj.zhxy.mapper.GradeMapper;
import com.yj.zhxy.pojo.Grade;
import com.yj.zhxy.service.GradeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author YangJian
 * @date 2023/12/31 14:22
 * @description
 */
@Service
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {

    @Override
    public IPage<Grade> getGradeByOpr(Page<Grade> page, String gradeName) {
        QueryWrapper<Grade> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(gradeName)){
            queryWrapper.like("name",gradeName);
        }
        queryWrapper.orderByDesc("id");
        Page<Grade> gradePage = baseMapper.selectPage(page,queryWrapper);
        return gradePage;
    }

    @Override
    public List<Grade> getGradeList() {
        return baseMapper.selectList(null);
    }
}
