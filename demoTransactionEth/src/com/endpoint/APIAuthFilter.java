package com.endpoint;

import com.entity.APICredential;
import com.entity.ResponseMessage;
import com.util.RESTUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class APIAuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String APIKey = request.getHeader("CB-ACCESS-KEY");
        String APISecretKey = request.getHeader("CB-ACCESS-SIGN");
        long timestamp = Long.valueOf(request.getHeader("CB-ACCESS-TIMESTAMP"));
        if ((System.currentTimeMillis() - timestamp * 1000) > (30 * 1000)) {
            response.setStatus(403);
            ResponseMessage msg = new ResponseMessage(403, "Request is expired", "Request must be within 30 seconds");
            response.getWriter().print(RESTUtil.gson.toJson(msg));
            return;
        }
        APICredential credential = ofy().load().type(APICredential.class).id(APIKey).now();
        if (credential == null) {
            response.setStatus(403);
            ResponseMessage msg = new ResponseMessage(403, "Forbidden", "API key is invalid");
            response.getWriter().print(RESTUtil.gson.toJson(msg));
            return;
        }
        if (credential.getAPISecretKey().equals(APISecretKey) == false) {
            response.setStatus(403);
            ResponseMessage msg = new ResponseMessage(403, "Forbidden", "ACCESS-SIGN is invalid");
            response.getWriter().print(RESTUtil.gson.toJson(msg));
            return;
        }
        filterChain.doFilter(request, response);


    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis() / 1000);

    }

    @Override
    public void destroy() {

    }
}
