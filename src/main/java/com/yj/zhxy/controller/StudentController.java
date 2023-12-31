package com.yj.zhxy.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yj.zhxy.pojo.Student;
import com.yj.zhxy.service.StudentService;
import com.yj.zhxy.util.MD5;
import com.yj.zhxy.util.ResponseObject;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "学生控制器")
@RestController
@RequestMapping("/sms/studentController")
public class StudentController {
    @Autowired
    private StudentService studentService;


    @Parameter(description = "分页带条件查询学生信息")
    @GetMapping("/getStudentByOpr/{pageNo}/{pageSize}")
    public ResponseObject getStudentBuOpr(
        @Parameter(description = "页码数") @PathVariable("pageNo") Integer pageNo,
        @Parameter(description = "页大小") @PathVariable("pageSize") Integer pageSize,
        @Parameter(description = "查询的条件") Student student
    ){
        //分页信息封装Page对象
        Page<Student> pageParam =new Page(pageNo,pageSize);
        // 进行查询
        IPage<Student> studentPage =studentService.getStudentByOpr(pageParam,student);
        // 封装ResponseObject返回
        return ResponseObject.ok(studentPage);
    }


    @Parameter(description = "删除单个或者多个学生信息")
    @DeleteMapping("/delStudentById")
    public ResponseObject delStudentById(
            @Parameter(description = "要删除的学生编号的JSON数组") @RequestBody List<Integer> ids
    ){
        studentService.removeByIds(ids);
        return ResponseObject.ok();
    }


    // POST  http://localhost:9002/sms/studentController/addOrUpdateStudent

    @Parameter(description = "保存或者修改学生信息")
    @PostMapping("/addOrUpdateStudent")
    public ResponseObject addOrUpdateStudent(
            @Parameter(description = "要保存或修改的学生JSON")@RequestBody Student student
    ){
        Integer id = student.getId();
        if (null == id || 0 ==id) {
            student.setPassword(MD5.encrypt(student.getPassword()));
        }
        studentService.saveOrUpdate(student);
        return ResponseObject.ok();
    }

}
