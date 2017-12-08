package com.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.*;

import java.io.StringWriter;

public class WalletUtil {

    public static String createWalletFile(String password) {
        try {
            ECKeyPair keyPair = Keys.createEcKeyPair();
            WalletFile walletfile = Wallet.create(password, keyPair, 5, 10);
            ObjectMapper om = new ObjectMapper();
            StringWriter stringwriter = new StringWriter();
            om.writeValue(stringwriter, walletfile);
            stringwriter.flush();
            String wallet = stringwriter.getBuffer().toString();
            System.out.println(wallet);
            return wallet;
        } catch (Exception e) {
            return null;
        }
    }

//    public static void main(String[] args) {
//        WalletUtil.createWalletFile("1234");
//    }
}
