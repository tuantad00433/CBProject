package com.entity.Enum;

public enum AccountType{
    WALLET("wallet"),
    FIAT("fiat"),
    MULTISIG("multisig"),
    VAULT("vault"),
    MULTISIG_VAULT("multisig_vault");

    private final String text;
    private AccountType(final String text){
        this.text = text;
    }
    @Override
    public String toString(){
        return text;
    }
}
