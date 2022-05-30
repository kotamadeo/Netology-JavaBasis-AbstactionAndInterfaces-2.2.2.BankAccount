package com.gmail.at.kotamadeo.bills;

public class Bill {
    private double amount;
    private final String tittle;

    public Bill(String tittle) {
        this.tittle = tittle;
    }


    public void setAmount(double amount) {
        if (amount >= 0) {
            this.amount = amount;
        }
    }

    public double getAmount() {
        return amount;
    }

    public String getTittle() {
        return tittle;
    }
}
