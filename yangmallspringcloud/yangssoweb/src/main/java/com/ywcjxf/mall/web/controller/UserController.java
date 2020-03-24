package com.ywcjxf.mall.web.controller;

import com.ywcjxf.mall.pojo.User;
import com.ywcjxf.mall.serviceinterface.UserService;
import com.ywcjxf.mall.utils.GraphicHelper;
import com.ywcjxf.mall.web.filter.RedisSessionFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.ywcjxf.mall.constant.Domain.*;
import static com.ywcjxf.mall.constant.Session.*;
import static com.ywcjxf.mall.service.constant.ArrayListOrHashMapConstants.HASH_MAP_DEFAULT_INITIAL_CAPACITY;

@Controller
public class UserController {
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping("/login_register")
    public String loginOrRegisterPage(ModelMap map,HttpServletRequest request){
        String referer = request.getHeader("Referer");

        RedisSessionFilter.RedisSession session = (RedisSessionFilter.RedisSession)request.getAttribute(REDIS_SESSION);
        String toUrl = (String)session.getAttribute("returnUrl");
        if(toUrl!=null && !toUrl.isEmpty()){
            referer = toUrl;
            //System.out.println(toUrl);
            logger.info("returnUrl "+toUrl);
            session.removeAttribute("returnUrl");
        }

        if(referer==null||referer.isEmpty()){
            referer=INDEX_SITE;
        }

        map.addAttribute("referer",referer);
        return "register_login";
    }

    @RequestMapping(value = "/captcha",method = RequestMethod.GET)
    public void getCaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpResponse) throws IOException {
        RedisSessionFilter.RedisSession session = (RedisSessionFilter.RedisSession)httpServletRequest.getAttribute(REDIS_SESSION);

        final int width = 180; // 图片宽度
        final int height = 40; // 图片高度
        final String imgType = "jpeg"; // 指定图片格式 (不是指MIME类型)
        final OutputStream output = httpResponse.getOutputStream(); // 获得可以向客户端返回图片的输出流
        // (字节流)
        // 创建验证码图片并返回图片上的字符串
        String code = GraphicHelper.create(width, height, imgType, output);

        session.setAttribute("Captcha", code);
        session.removeAttribute("code");
        session.setMaxInactiveInterval(FIVE_MIN_AS_SECONDS);//5min
    }


    @RequestMapping(value = "/checkcaptcha",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> checkCaptcha(@RequestAttribute(REDIS_SESSION) RedisSessionFilter.RedisSession redisSession,String data,String mobile){
        String captcha = (String)redisSession.getAttribute("Captcha");
        Map<String,Object> map = new HashMap<>(HASH_MAP_DEFAULT_INITIAL_CAPACITY);
        if(data!=null && data.equals(captcha)){
            map.put("code",0);
            if(mobile!=null && !mobile.isEmpty()) {
                int messageCode = userService.sendMessageCode(mobile);
                redisSession.setAttribute("code",messageCode);
                redisSession.setMaxInactiveInterval(ONE_MIN_AS_SECONDS);//1min
            }
        }else{
            map.put("code",1);
        }
        return map;
    }

    @RequestMapping(value = "/login_o_register",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> loginOrRegister(HttpServletRequest request,String mobile,String sms_captcha,String sms_captcha2,@RequestAttribute(REDIS_SESSION) RedisSessionFilter.RedisSession redisSession){
        Map<String,Object> map = new HashMap<>(HASH_MAP_DEFAULT_INITIAL_CAPACITY);
        String[] msg = new String[3];
        int n=0;
        if(mobile==null || !mobile.matches("^1[0-9]{10}$")){
            msg[n] = "手机号码格式不正确";
            n++;
        }
        String captcha = (String)redisSession.getAttribute("Captcha");
        if(sms_captcha2==null||!sms_captcha2.equals(captcha)){
            msg[n] = "图片验证码错误，请重新验证";
            n++;
        }
        String messageCode = redisSession.getAttribute("code")+"";
        //System.out.println(messageCode);
        //System.out.println(sms_captcha);

        logger.info("messageCode:"+messageCode);
        logger.info("sms_captcha"+sms_captcha);
        if(sms_captcha==null||messageCode.equals("null")||!sms_captcha.equals(messageCode)){
            msg[n] = "短信验证码错误，请重新验证";
            n++;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for(String s:msg){
            if(s==null){
                break;
            }

            stringBuilder.append(s).append("\n");
        }
        if(n==0){
            map.put("code",200);

            User user = new User();
            user.setLastIp(request.getRemoteAddr());
            user.setLastVisit(new Date());

            User user1 = userService.loginOrRegister(mobile,user);
            redisSession.setAttribute(USER,user1);//这里硬编码了
            redisSession.setMaxInactiveInterval(ONE_DAY_AS_SECONDS);

            redisSession.removeAttribute("code");
        }else{
            map.put("code",400);
            map.put("msg", stringBuilder.toString());
            //System.out.println(stringBuilder.toString());
            if(logger.isDebugEnabled()) {
                logger.debug(stringBuilder.toString());
            }
        }

        return map;
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request,@RequestAttribute(REDIS_SESSION) RedisSessionFilter.RedisSession redisSession){
        redisSession.removeAttribute(USER);


        String referer = request.getHeader("Referer");
        if(referer==null||referer.isEmpty()){
            referer=INDEX_SITE;
        }

        //return "redirect:http://"+referer;
        return "redirect:"+referer;

        //当从一个要登录的界面退出的话 上面的重定向 试图到要登录的界面 但是要登录 又重定向到登录页
    }

    @ResponseBody
    @RequestMapping("/logininfo")
    @CrossOrigin(origins = {INDEX_SITE,SEARCH_SITE,ITEM_SITE,CART_SITE,ORDER_SITE,PAY_SITE},methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT},allowedHeaders = {"X-Requested-With","Content-Type","Accept"},allowCredentials = "true")
    public Map<String,Object> loginInformation(HttpServletRequest request,@RequestAttribute(REDIS_SESSION) RedisSessionFilter.RedisSession redisSession){
        /*
        Cookie[] cookies = request.getCookies();
        boolean flag = false;
        String id = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    flag = true;
                    id = cookie.getValue();
                    break;
                }
            }
        }//这一部分在RedisSessionFilter那里也有

        */


        Map<String,Object> map = new HashMap<>(HASH_MAP_DEFAULT_INITIAL_CAPACITY);
        User user = (User)redisSession.getAttribute(USER);
        if(user!=null){
            map.put("status",1);
            String phoneNumber = user.getPhoneNumber();
            phoneNumber = phoneNumber.substring(0,3)+"****"+phoneNumber.substring(7);
            map.put("phoneNumber",phoneNumber);

            redisSession.setMaxInactiveInterval(ONE_DAY_AS_SECONDS);
        }else{
            map.put("status",0);

            redisSession.setMaxInactiveInterval(FIVE_MIN_AS_SECONDS);
        }
        return map;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
