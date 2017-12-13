package com.endpoint.cron;

import com.entity.Account;
import com.entity.Address;
import com.entity.POJO.Balance;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class UpdateBalanceCron extends HttpServlet {
    private Logger LOGGER = Logger.getLogger(UpdateBalanceCron.class.getSimpleName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ListIterator<Address> iteratorObj = ofy().load().type(Address.class).list().listIterator();
        LOGGER.info("Starting Iterator----------------");
        while (iteratorObj.hasNext()) {
            Address address = iteratorObj.next();
//            Updating Balance of Account and Address.
//            <-------->
            Balance lastestBalance = address.getLastestBalance();
            if ((lastestBalance.getAmount() - address.getBalance().getAmount()) == 0) {
                break;
            }
            Account account = ofy().load().type(Account.class).id(address.getAccountId()).now();
//            Updating Balance of Account.
            Balance balanceAccount = account.getBalance();
            balanceAccount.setAmount(balanceAccount.getAmount() + (lastestBalance.getAmount() - address.getBalance().getAmount()));
            account.setBalance(balanceAccount);

//            Updating address balance and insert to GAE both account and address.
            LOGGER.info("Save entity into DataStore............");
            address.setBalance(lastestBalance);
            if (ofy().save().entity(address).now() == null || ofy().save().entity(account).now() == null) {
                LOGGER.severe("Error in saving process");
            }
        }
    }
}
