package com.entity.Enum;

public enum NotificationType {
    PING("ping"),
    ADDRESSES_NEWPAYMENT("wallet:addresses:new-payment"),
    BUYS_CREATED("wallet:buys:created"),
    BUYS_COMPLETED("wallet:buys:completed"),
    BUYS_CANCELED("wallet:buys:canceled"),
    SELLS_CREATED("wallet:sells:created"),
    SELLS_COMPLETED("wallet:sells:completed"),
    SELLS_CANCELED("wallet:sells:canceled"),
    DEPOSIT_CREATED("wallet:deposit:created"),
    DEPOSIT_COMPLETED("wallet:deposit:completed"),
    DEPOSIT_CANCELED("wallet:deposit:canceled"),
    WITHDRAWAL_CREATED("wallet:withdrawal:created"),
    WITHDRAWAL_COMPLETED("wallet:withdrawal:completed"),
    WITHDRAWAL_CANCELED("wallet:withdrawal:canceled"),
    ORDERS_PAID("wallet:orders:paid"),
    ORDERS_MISPAID("wallet:orders:mispaid"),
    MERCHANTPAYOUT_CREATED("wallet:merchant-payouts:created");

    private final String text;
    private NotificationType(final String text){
        this.text = text;
    }
    @Override
    public String toString(){
        return text;
    }

}
