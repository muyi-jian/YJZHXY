package com.yj.zhxy.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yj.zhxy.pojo.Teacher;
import com.yj.zhxy.service.TeacherService;
import com.yj.zhxy.util.MD5;
import com.yj.zhxy.util.ResponseObject;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "教师控制器")
@RestController
@RequestMapping("/sms/teacherController")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;


    @Parameter(description = "分页获取教师信息,带搜索条件")
    @GetMapping("/getTeachers/{pageNo}/{pageSize}")
    public ResponseObject getTeachers(
            @Parameter(description = "页码数") @PathVariable("pageNo") Integer pageNo ,
            @Parameter(description = "页大小") @PathVariable("pageSize") Integer pageSize ,
            @Parameter(description = "查询条件") Teacher teacher
    ){
        Page<Teacher> paraParam =new Page<>(pageNo,pageSize);

        IPage<Teacher> page = teacherService.getTeachersByOpr(paraParam,teacher);

        return ResponseObject.ok(page);
    }


    @Parameter(description = "添加或者修改教师信息")
    @PostMapping("/saveOrUpdateTeacher")
    public ResponseObject saveOrUpdateTeacher(
            @Parameter(description = "要保存或者修改的JOSN格式的Teacher") @RequestBody Teacher teacher
    ){

        // 如果是新增,要对密码进行加密
        if (teacher.getId() == null || teacher.getId() ==0){
            teacher.setPassword(MD5.encrypt(teacher.getPassword()));
        }

        teacherService.saveOrUpdate(teacher);
        return  ResponseObject.ok();
    }

    @Parameter(description = "删除单个或者多个教师信息")
    @DeleteMapping("/deleteTeacher")
    public ResponseObject deleteTeacher(
           @Parameter(description = "要删除的教师信息的id的JOSN集合")  @RequestBody List<Integer> ids
    ){
        teacherService.removeByIds(ids);
        return ResponseObject.ok();
    }


}
