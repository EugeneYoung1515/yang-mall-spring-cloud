package com.ywcjxf.mall.web.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

public class CaptchaUsernamePasswordToken extends UsernamePasswordToken {

    private String captcha;


    public CaptchaUsernamePasswordToken(String userName, String password,
                                        boolean rememberMe,String host,String captcha) {
        super(userName, password, rememberMe,host);
        this.captcha = captcha;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
