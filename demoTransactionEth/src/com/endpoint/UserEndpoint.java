package com.endpoint;

import com.entity.APICredential;
import com.entity.ResponseMessage;
import com.entity.User;
import com.util.RESTUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class UserEndpoint extends HttpServlet {
    private static Logger LOGGER_UserEndpoint = Logger.getLogger(UserEndpoint.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uriSplit[] = req.getRequestURI().split("/");
        int action; //1-access common information of any user, 2-Do with current User.
        String userStr = uriSplit[2];
        switch (userStr) {
            case "users":
                action = 1;
                break;
            case "user":
                action = 2;
                break;
            default:
                resp.setStatus(500);
                ResponseMessage msg = new ResponseMessage(500, "Severe Error", "Severe Error");
                resp.getWriter().print(RESTUtil.gson.toJson(msg));
                return;
        }
        switch (action) {
            case 1:
                getUserById(req, resp, uriSplit);
                break;
            case 2:
                getCurrentUser(req, resp);
                break;
            default:
                resp.setStatus(500);
                ResponseMessage msg = new ResponseMessage(500, "Internal Error", "Contact admin for support");
                resp.getWriter().print(RESTUtil.gson.toJson(msg));
                return;
        }

    }

    private void getCurrentUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String APIKey = req.getHeader("CB-ACCESS-KEY");
        APICredential credential = ofy().load().type(APICredential.class).id(APIKey).now();
        User currentUser = ofy().load().type(User.class).id(credential.getUserId()).now();
        resp.getWriter().print(RESTUtil.gson.toJson(currentUser));
    }

    private void getUserById(HttpServletRequest req, HttpServletResponse resp, String[] uriSplit) throws IOException {
        if (uriSplit.length < 4) {
            resp.setStatus(400);
            ResponseMessage msg = new ResponseMessage(400, "Bad Request", "Invalid parameter");
            resp.getWriter().print(RESTUtil.gson.toJson(msg));
            return;
        }
        String userId = uriSplit[uriSplit.length - 1];
        User obj = ofy().load().type(User.class).id(userId).now();
        if (obj == null) {
            resp.setStatus(403);
            ResponseMessage msg = new ResponseMessage(403, "Not Found", "User not Found");
            return;
        }
        resp.setStatus(200);
        resp.getWriter().print(RESTUtil.gson.toJson(obj));
    }
}
