package com.ywcjxf.mall.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.ywcjxf.mall.constant.Domain.COOKIE_DOMAIN_TOP_SITE;
import static com.ywcjxf.mall.constant.Session.*;

public class RedisSessionFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RedisSessionFilter.class);

    @Lazy
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //System.out.println("filter");
        //System.out.println(((HttpServletRequest)servletRequest).getRequestURL());

        //System.out.println("g");

        if(logger.isDebugEnabled()) {
            logger.debug("filter: url:" + ((HttpServletRequest) servletRequest).getRequestURL());
        }

        filterChain.doFilter(new RedisHttpRequest((HttpServletRequest)servletRequest,(HttpServletResponse)servletResponse,redisTemplate),servletResponse);
    }

    @Override
    public void destroy() {

    }

    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public static class RedisHttpRequest extends HttpServletRequestWrapper {
        private HttpServletResponse response;
        private RedisTemplate<String,String> redisTemplate;

        private RedisSession redisSession;

        public RedisHttpRequest(HttpServletRequest request, HttpServletResponse response,RedisTemplate<String,String> redisTemplate) {
            super(request);
            this.response = response;
            this.redisTemplate = redisTemplate;
        }

        @Override
        public Object getAttribute(String name) {
            if(REDIS_SESSION.equals(name)){
                if(redisSession == null) {
                    Cookie[] cookies = getCookies();
                    boolean flag = false;
                    String id = null;
                    if (cookies != null) {
                        for (Cookie cookie : cookies) {
                            if (cookie.getName().equals(SESSION_COOKIE_NAME)) {
                                flag = true;
                                id = cookie.getValue();
                                break;
                            }
                        }
                    }

                    if (!flag) {
                        id = UUID.randomUUID().toString();
                        setTokenCookie(id);

                        redisSession = new RedisSession(id, redisTemplate);

                        //setAttribute("RedisSession",redisSession);//

                        return redisSession;
                    }else{
                        redisSession = new RedisSession(id, redisTemplate, this);
                        //System.out.println("else");

                        logger.debug("getAttribute else");

                        return redisSession;
                    }
                }
                return redisSession;
            }

            //前端传来cookie
            //前端传来的cookie后端没有对应的session
            return super.getAttribute(name);
        }

        public void setTokenCookie(String id){

            Cookie cookie= new Cookie(SESSION_COOKIE_NAME, id);
            cookie.setDomain(COOKIE_DOMAIN_TOP_SITE);
            cookie.setPath("/");

            response.addCookie(cookie);
        }
    }

    public static class RedisSession{
        private String id;
        private RedisTemplate<String,String> redisTemplate;

        public RedisSession(String id, RedisTemplate<String, String> redisTemplate,RedisHttpRequest request) {

            if(!redisTemplate.hasKey(SESSION_PREFIX+id)){
                String id2 = UUID.randomUUID().toString();
                request.setTokenCookie(id2);
                this.id = id2;
            }else{
                this.id = id;
            }
            this.redisTemplate = redisTemplate;

            HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
            hashOperations.put(SESSION_PREFIX+this.id,SESSION_INIT,SESSION_INIT);
        }

        public RedisSession(String id, RedisTemplate<String, String> redisTemplate) {
            this.id = id;
            this.redisTemplate = redisTemplate;

            HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
            hashOperations.put(SESSION_PREFIX+id,SESSION_INIT,SESSION_INIT);
        }

        public void setMaxInactiveInterval(int i) {
            redisTemplate.expire(SESSION_PREFIX+id,i, TimeUnit.SECONDS);//这个实现好想和HttpSession接口的setMaxInactiveInterval的不一样
        }


        public Object getAttribute(String s) {
            HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
            return hashOperations.get(SESSION_PREFIX+id, s);
        }


        public void setAttribute(String s, Object o) {
            HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
            hashOperations.put(SESSION_PREFIX+id,s,o);
        }


        public void removeAttribute(String s) {
            HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
            hashOperations.delete(SESSION_PREFIX+id,s);
        }


        public void invalidate() {
            redisTemplate.delete(SESSION_PREFIX+id);
        }
    }
}
//这里面很多字符串可以用常量换掉
