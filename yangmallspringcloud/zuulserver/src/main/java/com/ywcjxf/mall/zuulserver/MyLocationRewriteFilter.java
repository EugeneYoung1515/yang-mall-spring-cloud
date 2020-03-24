package com.ywcjxf.mall.zuulserver;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.netflix.util.Pair;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UrlPathHelper;

import java.net.URI;
import java.util.Iterator;

public class MyLocationRewriteFilter extends ZuulFilter {
    private final UrlPathHelper urlPathHelper = new UrlPathHelper();
    @Autowired
    private ZuulProperties zuulProperties;
    @Autowired
    private RouteLocator routeLocator;
    private static final String LOCATION_HEADER = "Location";

    public MyLocationRewriteFilter() {
    }

    public MyLocationRewriteFilter(ZuulProperties zuulProperties, RouteLocator routeLocator) {
        this.routeLocator = routeLocator;
        this.zuulProperties = zuulProperties;
    }

    public String filterType() {
        return "post";
    }

    public int filterOrder() {
        return 900;
    }

    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        int statusCode = ctx.getResponseStatusCode();
        return HttpStatus.valueOf(statusCode).is3xxRedirection();
    }

    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        Route route = this.routeLocator.getMatchingRoute(this.urlPathHelper.getPathWithinApplication(ctx.getRequest()));
        if (route != null) {
            Pair<String, String> lh = this.locationHeader(ctx);
            if (lh != null) {
                String location = (String)lh.second();
                URI originalRequestUri = UriComponentsBuilder.fromHttpRequest(new ServletServerHttpRequest(ctx.getRequest())).build().toUri();
                UriComponentsBuilder redirectedUriBuilder = UriComponentsBuilder.fromUriString(location);
                UriComponents redirectedUriComps = redirectedUriBuilder.build();
                String newPath = this.getRestoredPath(this.zuulProperties, route, redirectedUriComps);
                String modifiedLocation = redirectedUriBuilder.scheme(originalRequestUri.getScheme()).host(originalRequestUri.getHost()).port(originalRequestUri.getPort()).replacePath(newPath).build().toUriString();
                lh.setSecond(modifiedLocation);
            }
        }

        return null;
    }

    private String getRestoredPath(ZuulProperties zuulProperties, Route route, UriComponents redirectedUriComps) {
        StringBuilder path = new StringBuilder();
        String redirectedPathWithoutGlobal = this.downstreamHasGlobalPrefix(zuulProperties) ? redirectedUriComps.getPath().substring(("/" + zuulProperties.getPrefix()).length()) : redirectedUriComps.getPath();
        if (this.downstreamHasGlobalPrefix(zuulProperties)) {
            path.append("/" + zuulProperties.getPrefix());
        } else {
            path.append(this.zuulHasGlobalPrefix(zuulProperties) ? "/" + zuulProperties.getPrefix() : "");
        }

        path.append(this.downstreamHasRoutePrefix(route) ? "" : "/" + route.getPrefix()).append(redirectedPathWithoutGlobal);
        return path.toString();
    }

    private boolean downstreamHasGlobalPrefix(ZuulProperties zuulProperties) {
        return !zuulProperties.isStripPrefix() && StringUtils.hasText(zuulProperties.getPrefix());
    }

    private boolean zuulHasGlobalPrefix(ZuulProperties zuulProperties) {
        return StringUtils.hasText(zuulProperties.getPrefix());
    }

    private boolean downstreamHasRoutePrefix(Route route) {
        return !route.isPrefixStripped() && StringUtils.hasText(route.getPrefix());
    }

    private Pair<String, String> locationHeader(RequestContext ctx) {
        if (ctx.getZuulResponseHeaders() != null) {
            Iterator var2 = ctx.getZuulResponseHeaders().iterator();

            while(var2.hasNext()) {
                Pair<String, String> pair = (Pair)var2.next();
                if (((String)pair.first()).equals("location")||((String)pair.first()).equals("Location")) {
                    return pair;
                }
            }
        }

        return null;
    }
}
