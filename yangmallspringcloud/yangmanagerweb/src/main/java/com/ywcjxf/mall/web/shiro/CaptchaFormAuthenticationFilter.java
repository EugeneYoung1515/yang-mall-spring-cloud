package com.ywcjxf.mall.web.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class CaptchaFormAuthenticationFilter extends FormAuthenticationFilter {

    public static final String DEFAULT_CAPTCHA_PARAM = "captcha";
    private String captchaParam = DEFAULT_CAPTCHA_PARAM;
    private String captchaSessionAttributeName;

    private static final Logger logger = LoggerFactory.getLogger(CaptchaFormAuthenticationFilter.class);

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {

        //System.out.println("create");
        logger.info("create");

        CaptchaUsernamePasswordToken token = createToken(request, response);


        try {
            doCaptchaValidate((HttpServletRequest) request, token);
            Subject subject = getSubject(request, response);
            subject.login(token);
            return onLoginSuccess(token, subject, request, response);

        } catch (AuthenticationException e) {
            return onLoginFailure(token,e,request,response);
        }
    }

    //验证码校验
    protected void doCaptchaValidate(HttpServletRequest request, CaptchaUsernamePasswordToken token) {

        // 从session中获取图形码字符串
        String captcha = (String) request.getSession().getAttribute(captchaSessionAttributeName);

        // 校验
        if (captcha == null || !captcha.equals(token.getCaptcha())) {
            throw new IncorrectCaptchaException();
        }

        request.getSession().removeAttribute(captchaSessionAttributeName);
    }

    @Override
    protected CaptchaUsernamePasswordToken createToken(ServletRequest request, ServletResponse response) {

        String username = getUsername(request);
        String password = getPassword(request);
        boolean rememberMe = isRememberMe(request);
        String host = getHost(request);
        String captcha = getCaptcha(request);

        return new CaptchaUsernamePasswordToken(username,password,rememberMe,host,captcha);
    }

    protected  String getCaptcha(ServletRequest request) {
        return WebUtils.getCleanParam(request, getCaptchaParam());
    }

    public String getCaptchaParam() {
        return captchaParam;
    }

    public void setCaptchaParam(String captchaParam) {
        this.captchaParam = captchaParam;
    }

    public void setCaptchaSessionAttributeName(String captchaSessionAttributeName) {
        this.captchaSessionAttributeName = captchaSessionAttributeName;
    }
}
