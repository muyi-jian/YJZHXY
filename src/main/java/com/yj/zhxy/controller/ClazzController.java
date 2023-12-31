package com.yj.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yj.zhxy.pojo.Clazz;
import com.yj.zhxy.service.ClazzService;
import com.yj.zhxy.util.ResponseObject;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author YangJian
 * @date 2023/12/31 14:17
 * @description
 */
@Tag(name = "班级控制器")
@RestController
@RequestMapping("/sms/clazzController")
public class ClazzController {


    @Autowired
    private ClazzService clazzService;

    @Parameter(description = "增加或者修改班级信息")
    @PostMapping("/saveOrUpdateClazz")
    public ResponseObject saveOrUpdateClazz(
            @Parameter(description = "JSON格式的班级信息")@RequestBody Clazz clazz
    ){
        clazzService.saveOrUpdate(clazz);
        return ResponseObject.ok();
    }


    @Parameter(description = "分页带条件查询班级信息")
    @GetMapping("/getClazzsByOpr/{pageNo}/{pageSize}")
    public ResponseObject getClazzByOpr(
            @Parameter(description = "分页查询的页码数") @PathVariable("pageNo") Integer pageNo,
            @Parameter(description = "分页查询的每页显示条数")@PathVariable("pageSize") Integer pageSize,
            @Parameter(description = "分页查询的查询条件") Clazz clazz
    ){
        Page<Clazz> page =new Page<>(pageNo,pageSize);

        IPage<Clazz> iPage=clazzService.getClazzsByOpr(page,clazz);

        return ResponseObject.ok(iPage);

    }
    @Parameter(description = "查询所有班级信息")
    @GetMapping("/getClazzs")
    public ResponseObject getClazzs(){
        List<Clazz> clazzes= clazzService.getClazzs();
        return ResponseObject.ok(clazzes);
    }



    //DELETE sms/clazzController/deleteClazz  [1,2,3]
    @Parameter(description = "删除单个和多个班级")
    @DeleteMapping("/deleteClazz")
    public ResponseObject deleteClazz(
            @Parameter(description = "要删除的多个班级的ID的JSON数组")@RequestBody List<Integer> ids
    ){
        clazzService.removeByIds(ids);

        return ResponseObject.ok();
    }


}
