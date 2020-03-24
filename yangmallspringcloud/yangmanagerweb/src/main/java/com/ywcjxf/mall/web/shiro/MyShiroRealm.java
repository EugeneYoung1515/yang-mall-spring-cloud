package com.ywcjxf.mall.web.shiro;

import com.ywcjxf.mall.pojo.AdminUser;
import com.ywcjxf.mall.serviceinterface.AdminUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

public class MyShiroRealm extends AuthorizingRealm {

    private AdminUserService userService;

    private static final Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //System.out.println("here");
        logger.info("doGetAuthenticationInfo start");

        String username = (String) token.getPrincipal();
        AdminUser dbUser = userService.getAdminUserByUserName(username);
        //System.out.println(dbUser);

        logger.info(dbUser.toString());
        if (dbUser == null) {
            return null;
        }

        //验证通过返回一个封装了用户信息的AuthenticationInfo实例即可。
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                dbUser, //用户信息
                dbUser.getPassword(), //密码
                ByteSource.Util.bytes(username),
                getName() //realm name
        );
        return authenticationInfo;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals){return null;}

    @Lazy//加上这个注解 is not eligible for getting processed by all BeanPostProcessors的数量减少了但是有
    @Autowired
    public void setUserService(AdminUserService userService) {
        this.userService = userService;
    }


}

