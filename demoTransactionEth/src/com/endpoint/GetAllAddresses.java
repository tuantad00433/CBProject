package com.endpoint;

import com.entity.Address;
import com.entity.POJO.AddressListPOJO;
import com.util.RESTUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class GetAllAddresses extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Address> list = ofy().load().type(Address.class).list();
        AddressListPOJO listPOJO = AddressListPOJO.getInstance();
        listPOJO.setData(list);
        resp.getWriter().print(RESTUtil.gson.toJson(listPOJO));
    }
}
