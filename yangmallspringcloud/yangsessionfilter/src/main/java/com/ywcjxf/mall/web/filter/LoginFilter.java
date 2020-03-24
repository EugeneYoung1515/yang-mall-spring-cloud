package com.ywcjxf.mall.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ywcjxf.mall.pojo.User;

import static com.ywcjxf.mall.constant.Domain.REDIRECT_TO_LOGIN_URL;
import static com.ywcjxf.mall.constant.Session.FIVE_MIN_AS_SECONDS;
import static com.ywcjxf.mall.constant.Session.REDIS_SESSION;
import static com.ywcjxf.mall.constant.Session.USER;


public class LoginFilter implements Filter {
    private String[] noLoginUrls = {"/addcart",
            "/goods_num"};
    //不需要登录的url

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
            HttpServletRequest httpRequest = (HttpServletRequest) request;

        RedisSessionFilter.RedisSession redisSession = (RedisSessionFilter.RedisSession)request.getAttribute(REDIS_SESSION);

            User userContext = (User)redisSession.getAttribute(USER);

            if (userContext == null && !isURILogin(httpRequest.getRequestURI(), httpRequest)) {//!isURILogin需要登录
                redisSession.setMaxInactiveInterval(FIVE_MIN_AS_SECONDS);

                String toUrl = httpRequest.getRequestURL().toString();
                if(httpRequest.getQueryString()!=null && !httpRequest.getQueryString().isEmpty()){
                    toUrl += "?" + httpRequest.getQueryString();
                }

                redisSession.setAttribute("returnUrl",toUrl);

                HttpServletResponse httpResponse = (HttpServletResponse)response;
                httpResponse.sendRedirect(REDIRECT_TO_LOGIN_URL);

                return;
            }

            //redisSession.setMaxInactiveInterval(24*3600);
            chain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }


    //当前URI资源是否需要登录才能访问
    private boolean isURILogin(String requestURI, HttpServletRequest request) {//不需要登录
        //if (request.getContextPath().equalsIgnoreCase(requestURI) || (request.getContextPath() + "/").equalsIgnoreCase(requestURI)) {
            //return true;
        //}
        for (String uri : noLoginUrls) {
            if (requestURI != null && requestURI.indexOf(uri) >= 0) {
                return true;
            }
        }
        return false;
    }

    public void destroy() {
    }

    public void setNoLoginUrls(String[] noLoginUrls) {
        this.noLoginUrls = noLoginUrls;
    }
}

