package com.endpoint;

import com.entity.*;
import com.entity.Enum.CurrencyType;
import com.entity.Enum.NotificationType;
import com.entity.POJO.Balance;
import com.google.gson.JsonSyntaxException;
import com.util.RESTUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;
import static com.googlecode.objectify.ObjectifyService.ofy;

public class NotificationEndpoint extends HttpServlet {
    private Logger LOGGER = Logger.getLogger(Notification.class.getSimpleName());
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String content = RESTUtil.readStringInput(req.getInputStream());
        try {
            BalanceChange balanceChange = RESTUtil.gson.fromJson(content,BalanceChange.class);
            Address address = ofy().load().type(Address.class).filter("address",balanceChange.getAddress()).first().now();
            Account account = ofy().load().type(Account.class).id(address.getAccountId()).now();
            User user = ofy().load().type(User.class).id(account.getUserId()).now();
            Notification notification = new Notification(account.getUserId(),account.getId());
//            Update address with new Balance.
            Balance balance = address.getBalance();
            balance.setAmount(balance.getAmount()+balanceChange.getChange());
            address.setBalance(balance);
            address.setUpdated_at(Time.getTimeUTC());
            if(ofy().save().entity(address).now()==null){
                LOGGER.severe("Saving to datastore failed, address has not been updated with new balance ");
            }
//            Set fields of Notification.
            notification.setType(NotificationType.ADDRESSES_NEWPAYMENT.toString());

//            Set Data.
            HashMap<String,Object> data = notification.getData();
            data.put("id",address.getId());
            data.put("address",address.getAddress());
            data.put("name",address.getName());
            data.put("created_at",address.getCreated_at());
            data.put("updated_at",address.getUpdated_at());
            data.put("resource","address");
            data.put("resource_path",address.getResource_path());
            notification.setData(data);

//            Set User.
            HashMap<String,Object> userNoti = notification.getUser();
            userNoti.put("resource","user");
            userNoti.put("resource_path",user.getResource_path());
            notification.setUser(userNoti);

//            Set Account.
            HashMap<String,Object> accountNoti = notification.getAccount();
            accountNoti.put("resource","account");
            accountNoti.put("resource_path",account.getResource_path());
            notification.setAccount(accountNoti);
//            Set additional _data.
            HashMap<String,Object> additional_data = notification.getAdditionalData();
            HashMap<String,Object> amountHash = new HashMap<>();
            amountHash.put("amount",balanceChange.getChange());
            amountHash.put("currency", CurrencyType.ETH.toString());
            additional_data.put("amount",amountHash);
            notification.setAdditionalData(additional_data);

//            POST to URL_Notification.
            String response = Jsoup.connect(account.getURLNotification())
                    .ignoreContentType(true)
                    .method(Connection.Method.POST)
                    .requestBody(RESTUtil.gson.toJson(notification))
                    .execute()
                    .body();
        }catch (JsonSyntaxException e){
            LOGGER.severe("PARSE GSON ERROR: "+e.getStackTrace().toString());
        }
    }
}
