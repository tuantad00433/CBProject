package com.endpoint.Filter;

import com.entity.MemberCredential;
import com.entity.ResponseMessage;
import com.util.RESTUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MemberFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        String authentication = request.getHeader("Authentication");
        MemberCredential credential = MemberCredential.loadCredential(authentication);
        if (credential==null){
            response.setStatus(403);
            ResponseMessage msg = new ResponseMessage(403,"Forbidden","Token is invalid or expired- "+authentication);
            response.getWriter().print(RESTUtil.gson.toJson(msg));
            return;
        }
        filterChain.doFilter(request,response);

    }

    @Override
    public void destroy() {}
}
