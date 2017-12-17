package com.main;

import com.entity.Address;
import com.entity.BalanceChange;
import com.entity.POJO.AddressListPOJO;
import com.util.RESTUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.http.HttpService;
import rx.Subscription;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class QueryBlockChain {
    public static void main(String[] args) throws Exception {


        Web3j web3j = Web3j.build(new HttpService("http://149.56.111.209:8545"));
        CountDownLatch countDownLatch = new CountDownLatch(1);
        web3j.catchUpToLatestAndSubscribeToNewTransactionsObservable(DefaultBlockParameterName.LATEST)//filter()
                .subscribe(tx -> {
                            System.out.println(tx.getFrom()+"--------------"+tx.getTo());
                            try {
                                String content = Jsoup.connect("https://1-dot-digitalwalletservice.appspot.com/v2/addresses").ignoreContentType(true).method(Connection.Method.GET).execute().body();
                                AddressListPOJO listPOJO = RESTUtil.gson.fromJson(content, AddressListPOJO.class);
                                ListIterator<Address> iterator = listPOJO.getData().listIterator();
                                while (iterator.hasNext()) {
                                    Address address = iterator.next();
                                    if (address.getAddress().equals(tx.getFrom())){
                                        System.out.println(tx.getFrom());
                                        BalanceChange balanceChange = BalanceChange.getInstance();
                                        balanceChange.setAddress(address.getAddress());
                                        balanceChange.setChanges(tx.getValue().doubleValue()*(-1));
                                     String response =   Jsoup.connect("https://1-dot-digitalwalletservice.appspot.com/v2/notifications")
                                                .ignoreContentType(true)
                                                .method(Connection.Method.POST)
                                                .requestBody(RESTUtil.gson.toJson(balanceChange))
                                                .execute()
                                                .body();
                                    };
                                    if(address.getAddress().equals(tx.getTo())){
                                        System.out.println(tx.getTo());
                                        BalanceChange balanceChange = BalanceChange.getInstance();
                                        balanceChange.setAddress(address.getAddress());
                                        balanceChange.setChanges(tx.getValue().doubleValue());
                                    String response=    Jsoup.connect("https://1-dot-digitalwalletservice.appspot.com/v2/notifications")
                                                .ignoreContentType(true)
                                                .method(Connection.Method.POST)
                                                .requestBody(RESTUtil.gson.toJson(balanceChange))
                                                .execute()
                                                .body();
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        , Throwable::printStackTrace
                        , countDownLatch::countDown);

        Thread.sleep(TimeUnit.MINUTES.toMillis(1));
    }


//    public static void main(String[] args) throws Exception {
//        Web3j web3j = Web3j.build(new HttpService("http://149.56.111.209:8545"));
//        CountDownLatch countDownLatch = new CountDownLatch(1);
//        web3j.catchUpToLatestAndSubscribeToNewTransactionsObservable(DefaultBlockParameterName.LATEST)//filter()
//                .subscribe(tx -> System.out.printf("Transaction from %s to %s value : %d\n",tx.getFrom(),tx.getTo(),tx.getValue()), Throwable::printStackTrace, countDownLatch::countDown);
//
//        Thread.sleep(TimeUnit.MINUTES.toMillis(1));
//    }
}