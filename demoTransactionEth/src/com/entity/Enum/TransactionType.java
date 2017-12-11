package com.entity.Enum;

public enum TransactionType {
    SEND("send"),
    REQUEST("request"),
    TRANSFER("transfer"),
    BUY("buy"),
    SELL("sell"),
    FIAT_DEPOSIT("fiat_deposit"),
    FIAT_WITHDRAWAL("fiat_withdrawal"),
    EXCHANGE_DEPOSIT("exchange_deposit"),
    EXCHANGE_WITHDRAWAL("exchange_withdrawal"),
    VAULT_WITHDRAWAL("vault_withdrawal");

    private final String text;
    private TransactionType(final String text){
        this.text = text;
    }
    @Override
    public String toString(){
        return text;
    }
}
