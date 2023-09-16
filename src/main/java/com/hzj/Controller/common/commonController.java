package com.hzj.Controller.common;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.ShearCaptcha;
import javafx.scene.transform.ShearBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * PackageName :com.hzj.Controller.common
 * ClassName: commonController
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/9  17:32
 * @edition 1.0
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class commonController {

    @GetMapping("/kaptcha")
    public void defaultKaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //没有缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        //设置过期的时间期限
        response.setDateHeader("Expires", 0);
        //Content-Type：该实体头的作用是让服务器告诉浏览器它发送的数据属于什么文件类型。
        response.setContentType("image/png");
        //生成验证码
        ShearCaptcha shearCaptcha = CaptchaUtil.createShearCaptcha(150, 30, 4, 2);
        //将验证码存在session中
        request.getSession().setAttribute("verifyCode", shearCaptcha);
        //输出图片流
        shearCaptcha.write(response.getOutputStream());
    }
}
