package com.endpoint;

import com.entity.*;
import com.google.gson.JsonSyntaxException;
import com.googlecode.objectify.Key;
import com.util.RESTUtil;
import com.util.WalletUtil;
//import com.util.WalletUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class MemberEndpoint extends HttpServlet {
    private static Logger LOGGER_MemberEndpoint = Logger.getLogger(MemberEndpoint.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String urlSplit[] = req.getRequestURI().split("/");
        if (urlSplit.length != 4) {
            resp.setStatus(400);
            ResponseMessage msg = new ResponseMessage(400, "Bad Request", "Required parameter is missing");
            resp.getWriter().print(RESTUtil.gson.toJson(msg));
            return;
        }
        String endpoint = urlSplit[urlSplit.length - 1];
        switch (endpoint) {
            case "login":
                doLogin(req, resp);
                break;
            case "register":
                doRegister(req, resp);
                break;
            default:
                resp.setStatus(400);
                ResponseMessage msg = new ResponseMessage(400, "Bad Request", "Invalid parameter");
                resp.getWriter().print(RESTUtil.gson.toJson(msg));
                break;
        }
    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String content = RESTUtil.readStringInput(request.getInputStream());
            Member checkingObj = RESTUtil.gson.fromJson(content, Member.class);
            Member obj = ofy().load().type(Member.class).filterKey(Key.create(Member.class,checkingObj.getEmail())).filter("status",1).first().now();
            if (obj == null) {
                response.setStatus(404);
                ResponseMessage msg = new ResponseMessage(404, "Not Found", "Email is not found or has been deactivated.");
                response.getWriter().print(RESTUtil.gson.toJson(msg));
                return;
            }

            if (obj.getPassword().equals(checkingObj.getPassword()) == false) {
                response.setStatus(403);
                ResponseMessage msg = new ResponseMessage(403, "Forbidden", "Password is incorrect");
                response.getWriter().print(RESTUtil.gson.toJson(msg));
                return;
            }
            MemberCredential credential = new MemberCredential(obj.getUserId());
            if (ofy().save().entity(credential).now() == null) {
                response.setStatus(500);
                ResponseMessage msg = new ResponseMessage(500, "Server Error", "Contact admin for support");
                response.getWriter().print(RESTUtil.gson.toJson(msg));
                return;
            }
            response.setStatus(200);
            response.getWriter().print(RESTUtil.gson.toJson(credential));
        } catch (JsonSyntaxException e) {
            response.setStatus(400);
            ResponseMessage msg = new ResponseMessage(400, "Bad Request", "Data is not well-form");
            response.getWriter().print(RESTUtil.gson.toJson(msg));
            return;
        }


    }

    private void doRegister(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String content = RESTUtil.readStringInput(request.getInputStream());
        Member obj = RESTUtil.gson.fromJson(content, Member.class);
        HashMap<String, String> errors = obj.validate();

        //Validate fields and duplicated email.
        if (errors.size() > 0) {
            response.setStatus(400);
            ResponseMessage msg = new ResponseMessage(400, "Bad Request", RESTUtil.gson.toJson(errors));
            response.getWriter().print(RESTUtil.gson.toJson(msg));
            return;
        }
        Member checkingObj = ofy().load().type(Member.class).id(obj.getEmail()).now();
        if (checkingObj != null) {
            response.setStatus(403);
            ResponseMessage msg = new ResponseMessage(403, "email is dupplicated", "Dupplicated email");
            response.getWriter().print(RESTUtil.gson.toJson(msg));
            return;
        }


        User objUser = new User(obj.getUserId());
        objUser.setName(obj.getFirstName());
        objUser.setUsername(obj.getFirstName());
        Account objAccount = new Account(obj.getUserId());
        objAccount.setPrimary(true);

        if (ofy().save().entity(obj).now() == null || ofy().save().entity(objUser).now() == null || ofy().save().entity(objAccount).now() == null) {
            response.setStatus(500);
            ResponseMessage msg = new ResponseMessage(500, "Server Error", "Contact admin for support");
            response.getWriter().print(RESTUtil.gson.toJson(msg));
            return;
        }
        response.setStatus(201);
        response.setContentType("application/json");
        response.getWriter().print(RESTUtil.gson.toJson(obj));

    }
}
