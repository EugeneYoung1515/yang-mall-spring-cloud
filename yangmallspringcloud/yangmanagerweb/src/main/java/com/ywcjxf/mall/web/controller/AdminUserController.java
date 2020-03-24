package com.ywcjxf.mall.web.controller;

import com.ywcjxf.mall.pojo.AdminUser;
import com.ywcjxf.mall.serviceinterface.AdminUserService;
import com.ywcjxf.mall.web.captcha.GraphicHelper;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;

@Controller
public class AdminUserController {

    private AdminUserService userService;

    @RequestMapping("/login.html")
    public String login(HttpServletRequest request){
        System.out.println(request.getHeader("host"));
        return "c_login";
    }

    //这个方法要不要单独自己一个类
    @RequestMapping(value = "/captcha",method = RequestMethod.GET)
    public void getCaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpResponse) throws IOException {
        HttpSession session = httpServletRequest.getSession();

        final int width = 180; // 图片宽度
        final int height = 40; // 图片高度
        final String imgType = "jpeg"; // 指定图片格式 (不是指MIME类型)
        final OutputStream output = httpResponse.getOutputStream(); // 获得可以向客户端返回图片的输出流
        // (字节流)
        // 创建验证码图片并返回图片上的字符串
        String code = GraphicHelper.create(width, height, imgType, output);

        session.setAttribute("Captcha", code);
    }

    @RequestMapping("/index.html")
    public ModelAndView index(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("c_index_2");
        return modelAndView;
    }

    @RequestMapping(value = "registerpage.html",method = RequestMethod.GET)
    public ModelAndView registerPage(){
        ModelAndView modelAndView = new ModelAndView("c_register");
        return modelAndView;
    }

    @RequestMapping(value = "register.html",method = RequestMethod.POST)
    public ModelAndView register(String userName, String password, String passwordagain, RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView("redirect:/registerpage.html");
        if(userName==null||userName.length()==0){
           redirectAttributes.addFlashAttribute("errorMsg","请填写登录名");
            return modelAndView;
        }

        if(password==null||passwordagain==null||password.length()==0||passwordagain.length()==0||!password.equals(passwordagain)){
            redirectAttributes.addFlashAttribute("errorMsg","未填写密码或两次密码不一致");
            return modelAndView;
        }

        Integer oneIsExist = userService.userNameExist(userName);
        if(oneIsExist>0){
            redirectAttributes.addFlashAttribute("errorMsg","登录名已存在请选择新的登录名");
            return modelAndView;
        }

        modelAndView.setViewName("redirect:/login.html");
        redirectAttributes.addFlashAttribute("msg","注册成功请登录");
        AdminUser adminUser = new AdminUser();
        adminUser.setUserName(userName);
        adminUser.setPassword(new SimpleHash("MD5",password, ByteSource.Util.bytes(userName),3).toHex());
        userService.register(adminUser);
        return modelAndView;

    }

    @Autowired
    public void setUserService(AdminUserService userService) {
        this.userService = userService;
    }
}
