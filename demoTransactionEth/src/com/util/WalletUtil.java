package com.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.*;

import java.io.StringWriter;
import java.util.HashMap;

public class WalletUtil {

    public static HashMap<String, String> createWalletFile(String password) {
        HashMap<String, String> wallet = new HashMap<>();
        try {
            ECKeyPair keyPair = Keys.createEcKeyPair();
            WalletFile walletfile = Wallet.create(password, keyPair, 5, 10);
            ObjectMapper om = new ObjectMapper();
            StringWriter stringwriter = new StringWriter();
            om.writeValue(stringwriter, walletfile);
            stringwriter.flush();
            String walletStr = stringwriter.getBuffer().toString();
            String walletAddress = walletfile.getAddress();
            wallet.put("wallet", walletStr);
            wallet.put("walletAddress", walletAddress);
            return wallet;
        } catch (Exception e) {
            return wallet;
        }
    }

    public static void main(String[] args) {
        WalletUtil.createWalletFile("1234");
    }
}
