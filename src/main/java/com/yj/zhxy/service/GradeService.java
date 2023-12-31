package com.yj.zhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yj.zhxy.pojo.Grade;

import java.util.List;

/**
 * @author YangJian
 * @date 2023/12/31 14:21
 * @description
 */
public interface GradeService extends IService<Grade> {
    IPage<Grade> getGradeByOpr(Page<Grade> page, String gradeName);
    List<Grade> getGradeList();
}
