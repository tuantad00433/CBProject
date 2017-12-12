package com.endpoint;

import com.entity.*;
import com.google.gson.JsonSyntaxException;
import com.util.RESTUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class APIKeyEndpoint extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            MemberCredential credential = ofy().load().type(MemberCredential.class).filter("tokenKey",req.getHeader("Authentication")).first().now();
            APICredential apiCredential = new APICredential(credential.getUserId());
            if (ofy().save().entity(apiCredential) == null) {
                resp.setStatus(500);
                ResponseMessage msg = new ResponseMessage(500, "Server Error", "Contact admin for support");
                resp.getWriter().print(RESTUtil.gson.toJson(msg));
                return;
            }
            resp.setStatus(200);
            resp.getWriter().print(RESTUtil.gson.toJson(apiCredential));
        } catch (JsonSyntaxException e) {
            resp.setStatus(400);
            ResponseMessage msg = new ResponseMessage(400, "Bad Request", "Data is not well-Form");
            resp.getWriter().print(RESTUtil.gson.toJson(msg));
            return;
        }
    }
}
