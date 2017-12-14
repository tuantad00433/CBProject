package com.main;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.http.HttpService;
import rx.Subscription;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class QueryBlockChain {
//    public static void main(String[] args) throws InterruptedException {
//        CountDownLatch countDownLatch = new CountDownLatch(1);
//        Web3j web3j = Web3j.build(new HttpService("https://rinkeby.infura.io/4ANomGN5hGuPbpCZEIoj"));
//        //
//        Subscription subscription = web3j.transactionObservable().subscribe(tx -> {
//            System.out.printf("Transacgion from %s to %s , amount : %d", tx.getFrom(), tx.getTo(), tx.getValue());
//        });
//    }
}