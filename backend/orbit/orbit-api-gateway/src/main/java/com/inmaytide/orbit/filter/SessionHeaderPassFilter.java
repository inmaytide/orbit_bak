//package com.inmaytide.orbit.filter;
//
//import com.inmaytide.orbit.commons.consts.Constants;
//import com.netflix.zuul.ZuulFilter;
//import com.netflix.zuul.context.RequestContext;
//
//import javax.servlet.http.HttpSession;
//
//public class SessionHeaderPassFilter extends ZuulFilter {
//    @Override
//    public String filterType() {
//        return "pre";
//    }
//
//    @Override
//    public int filterOrder() {
//        return 0;
//    }
//
//    @Override
//    public boolean shouldFilter() {
//        return true;
//    }
//
//    @Override
//    public Object run() {
//        HttpSession session = RequestContext.getCurrentContext().getRequest().getSession();
//        if (session != null) {
//            RequestContext.getCurrentContext().addZuulRequestHeader(Constants.HEADER_NAME_SESSION_ID, session.getId());
//        }
//        return null;
//    }
//}
