package com.endpoint;

import com.entity.APICredential;
import com.entity.Member;
import com.entity.ResponseMessage;
import com.entity.User;
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
        String content = RESTUtil.readStringInput(req.getInputStream());

        try {
            Member obj = RESTUtil.gson.fromJson(content, Member.class);
            String userId = obj.getUserId();
            if (userId == null || userId.length() == 0) {
                resp.setStatus(400);
                ResponseMessage msg = new ResponseMessage(400, "Bad Request", "UserId is missing");
                resp.getWriter().print(RESTUtil.gson.toJson(msg));
                return;
            }
            User checkingObj = ofy().load().type(User.class).id(userId).now();
            if (checkingObj == null) {
                resp.setStatus(400);
                ResponseMessage msg = new ResponseMessage(404, "Bad Request", "User is not found");
                resp.getWriter().print(RESTUtil.gson.toJson(msg));
                return;
            }
            APICredential apiCredential = new APICredential(userId);
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
