package com.ywcjxf.mall.zuulserver.session;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Base64;

//@Component
public class SessionFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();

        HttpSession httpSession = context.getRequest().getSession();

        //这一句有问题 会覆盖掉httprequest里的cookie 最后会只剩下SESSION=XXX
        //context.addZuulRequestHeader("Cookie","SESSION="+base64Encode(httpSession.getId()));

        return null;

    }

    private static String base64Encode(String value) {
        byte[] encodedCookieBytes = Base64.getEncoder().encode(value.getBytes());
        return new String(encodedCookieBytes);
    }
}
