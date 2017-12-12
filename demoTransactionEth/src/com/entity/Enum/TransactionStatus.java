package com.entity.Enum;

public enum TransactionStatus {
    PENDING("pending"),
    COMPLETED("completed"),
    FAILED("failed"),
    EXPIRED("expired"),
    CANCELED("canceled"),
    WAITING_FOR_SIGNATURE("waiting_for_signature"),
    WAITING_FOR_CLEARING("waiting_for_clearing");
    private final String text;
    private TransactionStatus(final String text){
        this.text = text;
    }
    @Override
    public String toString(){
        return text;
    }

}
