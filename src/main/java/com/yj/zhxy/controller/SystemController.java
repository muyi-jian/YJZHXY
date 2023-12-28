package com.yj.zhxy.controller;

import com.yj.zhxy.pojo.Admin;
import com.yj.zhxy.pojo.LoginForm;
import com.yj.zhxy.pojo.Student;
import com.yj.zhxy.pojo.Teacher;
import com.yj.zhxy.service.AdminService;
import com.yj.zhxy.service.StudentService;
import com.yj.zhxy.service.TeacherService;
import com.yj.zhxy.util.CreateVerifiCodeImage;
import com.yj.zhxy.util.JwtHelper;
import com.yj.zhxy.util.ResponseCodeEnum;
import com.yj.zhxy.util.ResponseObject;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author YangJian
 * @date 2023/12/26 9:03
 * @description
 */

@Tag(name = "系统控制器")
@RestController
@RequestMapping("/sms/system")
public class SystemController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    @Parameter(description = "通过token口令获取当前登录的用户信息的方法")
    @GetMapping("/getInfo")
    public ResponseObject getInfo(@Parameter(description = "token口令")@RequestHeader String token){
         // 判断token是否失效
        boolean expiration = JwtHelper.isExpiration(token);
        if (expiration){
            return ResponseObject.build(null, ResponseCodeEnum.TOKEN_ERROR);
        }
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);
        // 准备一个map用户存放响应的数据
        Map<String,Object> map=new LinkedHashMap<>();
        switch (userType) {
            case 1:
                Admin admin =adminService.getAdminById(userId);
                map.put("userType",1);
                map.put("user",admin);
                break;
            case 2:
                Student student =studentService.getStudentById(userId);
                map.put("userType",1);
                map.put("user",student);
                break;
            case 3:
                Teacher teacher =teacherService.getTeacherById(userId);
                map.put("userType",1);
                map.put("user",teacher);
                break;
        }
        return ResponseObject.ok(map);
    }

    @Parameter(description = "登录的方法")
    @PostMapping("/login")
    public ResponseObject login(@Parameter(description = "登录提交信息的form表单") @RequestBody LoginForm loginForm,HttpServletRequest request){
        System.out.println("loginForm==========="+loginForm.toString());
        HttpSession session = request.getSession();
        String sessionVerifiCode = (String) session.getAttribute("verifiCode");
        String loginFormVerifiCode = loginForm.getVerifiCode();
        if ("".equals(sessionVerifiCode) || sessionVerifiCode == null){
            return ResponseObject.fail().message("验证码失效,请刷新后重试");
        }
        System.out.println("loginFormVerifiCode=="+loginFormVerifiCode);
        System.out.println("sessionVerifiCode=="+sessionVerifiCode);
        if (!sessionVerifiCode.equals(loginFormVerifiCode)){
            return ResponseObject.fail().message("验证码有误,请输入后重试");
        }
        // 从session域中移除现有验证码
        session.removeAttribute("verifiCode");

        // 准备一个map用户存放响应的数据
        Map<String,Object> map=new LinkedHashMap<>();
        switch (loginForm.getUserType()){
            case 1:
                try {
                    Admin admin = adminService.login(loginForm);
                    if (admin != null){
                        map.put("token", JwtHelper.createToken(admin.getId().longValue(),1));
                    }else{
                        throw new RuntimeException("用户名或者密码有误");
                    }
                    return ResponseObject.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return ResponseObject.fail().message(e.getMessage());
                }
            case 2:
                try {
                    Student student = studentService.login(loginForm);
                    if (student != null){
                        map.put("token", JwtHelper.createToken(student.getId().longValue(),1));
                    }else{
                        throw new RuntimeException("用户名或者密码有误");
                    }
                    return ResponseObject.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return ResponseObject.fail().message(e.getMessage());
                }
            case 3:
                try {
                    Teacher teacher = teacherService.login(loginForm);
                    if (teacher != null){
                        map.put("token", JwtHelper.createToken(teacher.getId().longValue(),1));
                    }else{
                        throw new RuntimeException("用户名或者密码有误");
                    }
                    return ResponseObject.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return ResponseObject.fail().message(e.getMessage());
                }
        }
        return ResponseObject.fail().message("查无此用户");
    }

    @Parameter(description = "获取验证码图片")
    @GetMapping("/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response){
        // 获取验证码图片
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        // 获取验证码字符串
        String verifiCode = String.valueOf(CreateVerifiCodeImage.getVerifiCode());

        /* 将验证码放入当前请求域 */
        request.getSession().setAttribute("verifiCode",verifiCode);
        try {
            // 将验证码图片通过输出流做出响应
            ImageIO.write(verifiCodeImage,"JPEG",response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
