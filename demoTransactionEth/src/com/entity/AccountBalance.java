package com.entity;

import com.entity.Enum.CurrencyType;

public class AccountBalance {
   private double amount;
   private String currency;

   public AccountBalance(){
       currency = CurrencyType.ETH.toString();
       amount = 0;
   }
   public AccountBalance(double amount, String currency){
       this.amount = amount;
       this.currency = currency;
   }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
