package com.entity.Enum;

public enum CurrencyType {
    BTC("BTC"),
    ETH("ETH"),
    LTC("LTC"),
    USD("USD");

    private final String text;

    private CurrencyType(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
