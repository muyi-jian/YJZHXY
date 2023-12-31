package com.yj.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yj.zhxy.pojo.Grade;
import com.yj.zhxy.service.GradeService;
import com.yj.zhxy.util.ResponseObject;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author YangJian
 * @date 2023/12/31 14:20
 * @description
 */
@Tag(name = "年级控制器")
@RestController
@RequestMapping("/sms/gradeController")
public class GradeController {

    @Autowired
    public GradeService gradeService;



    @Parameter(description = "获取全部年级")
    @GetMapping("/getGrades")
    public ResponseObject getGradeList(){
        return ResponseObject.ok(gradeService.getGradeList());
    }
    @Parameter(description = "根据年级名称模糊查询，带分页")
    @GetMapping("/getGrades/{pageNo}/{pageSize}")
    public ResponseObject getGradeByOpr(
            @Parameter(description = "分页查询的页码数")  @PathVariable("pageNo")  Integer pageNo,
            @Parameter(description = "分页查询的每页显示条数") @PathVariable("pageSize")  Integer pageSize,
            @Parameter(description = "分页查询模糊匹配的名称") String gradeName){
        // 分页 带条件查询
        Page<Grade> page = new Page<>(pageNo,pageSize);
        // 服务层
        IPage<Grade> pageRo = gradeService.getGradeByOpr(page,gradeName);
        // 封装Result对象并返回
        return ResponseObject.ok(pageRo);
    }
    @Parameter(description = "保存或修改年级")
    @PostMapping("/saveOrUpdateGrade")
    public ResponseObject saveOrUpdateGrade(@Parameter(description = "JSON的Grade对象")@RequestBody Grade grade){
        gradeService.saveOrUpdate(grade);
        return  ResponseObject.ok();
    }

    @Parameter(description = "删除Grade信息")
    @DeleteMapping("/deleteGrade")
    public ResponseObject deleteGrade(@Parameter(description = "要删除的所有的grade的id的JSON集合")@RequestBody List<Integer> ids){
        gradeService.removeByIds(ids);
        return  ResponseObject.ok();
    }
}
