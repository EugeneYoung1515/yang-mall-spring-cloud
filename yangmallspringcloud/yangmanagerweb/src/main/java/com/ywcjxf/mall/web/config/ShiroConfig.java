package com.ywcjxf.mall.web.config;

import com.ywcjxf.mall.web.shiro.CaptchaFormAuthenticationFilter;
import com.ywcjxf.mall.web.shiro.MyShiroRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public MyShiroRealm myShiroRealm(){
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());

        CaptchaFormAuthenticationFilter captchaFormAuthenticationFilter = new CaptchaFormAuthenticationFilter();
        captchaFormAuthenticationFilter.setUsernameParam("userName");
        captchaFormAuthenticationFilter.setRememberMeParam("RememberMe");
        captchaFormAuthenticationFilter.setCaptchaSessionAttributeName("Captcha");

        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setRedirectUrl("/login.html");

        Map<String, Filter> filterMap = shiroFilterFactoryBean.getFilters();
        filterMap.put("authc", captchaFormAuthenticationFilter);
        filterMap.put("logout",logoutFilter);
        shiroFilterFactoryBean.setFilters(filterMap);

        shiroFilterFactoryBean.setLoginUrl("/login.html");
        shiroFilterFactoryBean.setSuccessUrl("/index.html");

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/", "anon");//这个没处理
        filterChainDefinitionMap.put("/index.html", "authc");

        //shiro能一个名对应多个值 多个值也就是多个过滤器 好像是用逗号隔开

        filterChainDefinitionMap.put("/logout.html", "logout");
        filterChainDefinitionMap.put("/login.html", "authc");

        filterChainDefinitionMap.put("/register.html","anon");
        filterChainDefinitionMap.put("/registerpage.html","anon");

        filterChainDefinitionMap.put("/uploadimage","authc");

        filterChainDefinitionMap.put("/image/upload","authc");

        filterChainDefinitionMap.put("/image/upload/result","authc");

        filterChainDefinitionMap.put("/**","anon");//user authc?
        //先放过css js等

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        securityManager.setRememberMeManager(cookieRememberMeManager());
        return securityManager;
    }

    //cookie对象;
    @Bean
    public SimpleCookie rememberMeCookie() {
        SimpleCookie simpleCookie = new SimpleCookie("RememberMe");

        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(2592000);
        return simpleCookie;
    }

    //cookie管理对象;
    @Bean
    public CookieRememberMeManager cookieRememberMeManager() {
        CookieRememberMeManager manager = new CookieRememberMeManager();
        manager.setCookie(rememberMeCookie());
        return manager;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(3);
        return hashedCredentialsMatcher;
    }


    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

}
