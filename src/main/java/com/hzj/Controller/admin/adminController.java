package com.hzj.Controller.admin;

import cn.hutool.captcha.ShearCaptcha;
import com.hzj.pojo.AdminUser;
import com.hzj.pojo.Blog;
import com.hzj.pojo.BlogCategory;
import com.hzj.service.AdminUserService;
import com.hzj.service.BlogCategoryService;
import com.hzj.service.BlogService;
import com.hzj.service.BlogTagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * PackageName :com.hzj.Controller.admin
 * ClassName: adminController
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/9  16:10
 * @edition 1.0
 */
@Controller
@Slf4j
@RequestMapping("/admin")
public class adminController {

    @Resource
    private AdminUserService adminUserService;
    @Resource
    private BlogCategoryService blogCategoryService;
    @Resource
    private BlogTagService blogTagService;
    @Resource
    private BlogService blogService;


    @GetMapping("/login")
    public String login() {
        return "admin/login";
    }

    @PostMapping(value = "/login")
    public String login(@RequestParam("userName") String userName,
                        @RequestParam("password") String password,
                        @RequestParam("verifyCode")String verifyCode,
                                     HttpSession session){
        //1.先判断控制，验证码不能为空
        if (!StringUtils.hasText(verifyCode)){
            log.info("验证码不为空");
            session.setAttribute("errorMsg","验证码不能为空");
            return "admin/login";//重新登录
        }
        //2.用户名或密码都不能为空
        if (!StringUtils.hasText(userName) || !StringUtils.hasText(password)) {
            log.info("用户名和密码不为空");
            session.setAttribute("errorMsg","用户名和密码不能为空");
            return "admin/login";
        }
        //先进行验证码的比对
        ShearCaptcha shearCaptcha = (ShearCaptcha)session.getAttribute("verifyCode");
        if (shearCaptcha ==null || !shearCaptcha.verify(verifyCode)){
            log.info("验证码不正确");
            session.setAttribute("errorMsg","验证码不正确");
            return "admin/login";
        }
        //进行数据库的查询进行密码与用户名的比对，数据库中的密码时进行了MD5的加密
        AdminUser adminUser = adminUserService.loginComparison(userName,password);
        System.out.println("adminUser = " + adminUser.getLoginUserName());
        if (adminUser != null){
            //用户名存在所以可以登录
            session.setAttribute("loginUser",adminUser.getNickName());
            session.setAttribute("loginUserId",adminUser.getAdminUserId());
            return "redirect:/admin/index";
        }else {
            session.setAttribute("errorMsg", "登陆失败");
            return "admin/login";
        }
    }
    @GetMapping({"", "/", "/index", "/index.html"})
    public String index(HttpServletRequest request) {
        request.setAttribute("path", "index");
        return "admin/index";
    }
//    @GetMapping("/profile")
//    public String profile(HttpServletRequest request) {
//        Integer loginUserId = (int) request.getSession().getAttribute("loginUserId");
//        AdminUser adminUser = adminUserService.(loginUserId);
//        if (adminUser == null) {
//            return "admin/login";
//        }
//        request.setAttribute("path", "profile");
//        request.setAttribute("loginUserName", adminUser.getLoginUserName());
//        request.setAttribute("nickName", adminUser.getNickName());
//        return "admin/profile";
//    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("loginUserId");
        request.getSession().removeAttribute("loginUser");
        request.getSession().removeAttribute("errorMsg");
        return "admin/login";
    }
    @GetMapping("/profile")
    public String profile(HttpServletRequest request) {
        Integer loginUserId = (int) request.getSession().getAttribute("loginUserId");
        AdminUser adminUser = adminUserService.getUserDetailById(loginUserId);
        if (adminUser == null) {
            return "admin/login";
        }
        request.setAttribute("path", "profile");
        request.setAttribute("loginUserName", adminUser.getLoginUserName());
        request.setAttribute("nickName", adminUser.getNickName());
        return "admin/profile";
    }

}
