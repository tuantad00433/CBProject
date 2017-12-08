package com.entity;

public class AccountBalance {
   private double amount;
   private String currency;

   public AccountBalance(){
       currency = CurrencyType.ETH;
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
