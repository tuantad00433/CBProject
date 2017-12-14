package com.endpoint;

import com.entity.APICredential;
import com.entity.Account;
import com.entity.Address;
import com.entity.POJO.AddressesPOJO;
import com.entity.POJO.Pagination;
import com.entity.ResponseMessage;
import com.google.gson.JsonSyntaxException;
import com.util.RESTUtil;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.googlecode.objectify.cmd.*;

public class AccAddrEndpoint extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int action = 1; //1-Get List Address of an account.
        String uriSplit[] = req.getRequestURI().split("/");
        switch (uriSplit.length) {
            case 5:
                action = 1;
                break;
            default:
                resp.setStatus(400);
                ResponseMessage msg = new ResponseMessage(400, "Bad Request", "URI is invalid");
                resp.getWriter().print(RESTUtil.gson.toJson(msg));
                return;
        }
        switch (action) {
            case 1:
                getListAddresses(req, resp, uriSplit);
                break;
            default:
                resp.setStatus(400);
                ResponseMessage msg = new ResponseMessage(400, "Bad Request", "URI is invalid");
                resp.getWriter().print(RESTUtil.gson.toJson(msg));
                return;
        }

    }

    private void getListAddresses(HttpServletRequest req, HttpServletResponse resp, String uriSplit[]) throws IOException {
        Pagination pagination = new Pagination();
        String accountId = uriSplit[uriSplit.length - 2];
        try {
            pagination.setPage(Integer.parseInt(req.getParameter("page")));
            pagination.setLimit(Integer.parseInt(req.getParameter("limit")));
        } catch (NumberFormatException e) {
            pagination.setPage(1);
            pagination.setLimit(10);
        }
        int page = pagination.getPage();
        int limit = pagination.getLimit();
        Query<Address> queryObj = ofy().load().type(Address.class).filter("accountId", accountId);
        int totalItems = queryObj.count();
        int totalPages = totalItems / limit + ((totalItems % limit) > 0 ? 1 : 0);
        List<Address> listObj = ofy().load().type(Address.class).limit(limit).offset((page - 1) * limit).filter("accountId", accountId).list();

        //Prepare for sending back data.

        pagination.setTotalItem(totalItems);
        pagination.setTotalPage(totalPages);
        AddressesPOJO objPOJO = new AddressesPOJO();
        objPOJO.setPagination(pagination);
        objPOJO.setData(listObj);
        resp.setStatus(200);
        resp.getWriter().print(RESTUtil.gson.toJson(objPOJO));

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int action = 1; //1-Create account, 2-Set Primary Status, 3- Create new Address.
        String uriSplit[] = req.getRequestURI().split("/");
        switch (uriSplit.length) {
            case 3:
                action = 1;
                break;
            case 5:
                if (uriSplit[uriSplit.length - 1].equalsIgnoreCase("addresses")) {
                    action = 3;
                    break;
                }

            default:
                resp.setStatus(400);
                ResponseMessage msg = new ResponseMessage(400, "Bad Request", "URI is invalid");
                resp.getWriter().print(RESTUtil.gson.toJson(msg));
                return;
        }

        switch (action) {
            case 1:
                createAccount(req, resp);
                break;
            case 2:
                updateAccountPrimary(req, resp);
                break;
            case 3:
                createAddress(req, resp);
                break;
            default:
                resp.setStatus(500);
                ResponseMessage msg = new ResponseMessage(500, "Severe Error", "Contact admin for support");
                resp.getWriter().print(RESTUtil.gson.toJson(msg));
                break;
        }
    }

    private void createAddress(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uriSplit[] = req.getRequestURI().split("/");
        try {
            String accountId = uriSplit[uriSplit.length - 2];
            String content = RESTUtil.readStringInput(req.getInputStream());
            Address checkingObj = RESTUtil.gson.fromJson(content, Address.class);
            Address address = new Address(accountId, checkingObj.getName());
            if (ofy().save().entity(address).now() == null) {
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
            Account obj = new Account(userId,null);
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
