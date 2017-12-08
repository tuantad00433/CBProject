package com.endpoint;

import com.entity.Address;
import com.entity.ResponseMessage;
import com.google.gson.JsonSyntaxException;
import com.util.RESTUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static com.googlecode.objectify.ObjectifyService.ofy;

public class AddressEndpoint extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uriSplit[] = req.getRequestURI().split("/");
        if (uriSplit.length != 5) {
            resp.setStatus(400);
            ResponseMessage msg = new ResponseMessage(400, "Bad Request", "invalid URI");
            resp.getWriter().print(RESTUtil.gson.toJson(msg));
            return;
        }
        if (uriSplit[uriSplit.length - 1].equalsIgnoreCase("addresses") == false) {
            resp.setStatus(400);
            ResponseMessage msg = new ResponseMessage(400, "Bad Request", "invalid URI");
            resp.getWriter().print(RESTUtil.gson.toJson(msg));
            return;
        }

        try {
            String accountId = uriSplit[uriSplit.length - 2];
            String content = RESTUtil.readStringInput(req.getInputStream());
            Address checkingObj = RESTUtil.gson.fromJson(content, Address.class);
            Address address = new Address(accountId,checkingObj.getName());
            if(ofy().save().entity(address).now()==null){
                resp.setStatus(500);
                ResponseMessage msg = new ResponseMessage(500, "Internal Server Error", "Contact admin for support");
                resp.getWriter().print(RESTUtil.gson.toJson(msg));
                return;
            }
            resp.getWriter().print(RESTUtil.gson.toJson(address));

        } catch (JsonSyntaxException e) {
            resp.setStatus(400);
            ResponseMessage msg = new ResponseMessage(400, "Bad Request", "data is not well-form");
            resp.getWriter().print(RESTUtil.gson.toJson(msg));
            return;
        }


    }
}
