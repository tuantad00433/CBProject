package com.endpoint;

import com.entity.APICredential;
import com.entity.Account;
import com.entity.ResponseMessage;
import com.google.gson.JsonSyntaxException;
import com.util.RESTUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.googlecode.objectify.ObjectifyService.ofy;
import static com.googlecode.objectify.ObjectifyService.pop;

public class AccountEndpoint extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int action = 1; //1-Create account, 2-Set Primary Status.
        String uriSplit[] = req.getRequestURI().split("/");
        if (uriSplit.length == 3) {
            action = 1;
        } else {
            action = 2;
        }
        switch (action) {
            case 1:
                createAccount(req, resp);
                break;
            case 2:
                updateAccountPrimary(req, resp);
                break;
            default:
                resp.setStatus(500);
                ResponseMessage msg = new ResponseMessage(500, "Severe Error", "Contact admin for support");
                resp.getWriter().print(RESTUtil.gson.toJson(msg));
                break;
        }
    }

    private void createAccount(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String content = RESTUtil.readStringInput(req.getInputStream());
            Account account = RESTUtil.gson.fromJson(content, Account.class);
            if (account.getName() == null || account.getName().length() == 0) {
                account.setName("My account");
            }
            String APIKey = req.getHeader("CB-ACCESS-KEY");
            APICredential credential = ofy().load().type(APICredential.class).id(APIKey).now();
            String userId = credential.getUserId();
            Account obj = new Account(userId);
            obj.setName(account.getName());
            if (ofy().save().entity(obj).now() == null) {
                resp.setStatus(500);
                ResponseMessage msg = new ResponseMessage(500, "Bad Request", "Json data is not well-form");
                resp.getWriter().print(RESTUtil.gson.toJson(msg));
                return;
            }
            resp.getWriter().print(RESTUtil.gson.toJson(obj));
        } catch (JsonSyntaxException e) {
            resp.setStatus(400);
            ResponseMessage msg = new ResponseMessage(400, "Bad Request", "Json data is not well-form");
            resp.getWriter().print(RESTUtil.gson.toJson(msg));
        }
    }

    private void updateAccountPrimary(HttpServletRequest req, HttpServletResponse resp) {
    }
}
