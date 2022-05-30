package com.gmail.at.kotamadeo.accounts.classes;

import com.gmail.at.kotamadeo.accounts.interfaces.Payable;
import com.gmail.at.kotamadeo.accounts.interfaces.Replenishable;
import com.gmail.at.kotamadeo.accounts.interfaces.Transferable;
import com.gmail.at.kotamadeo.utils.Utils;

public abstract class Account implements Transferable, Payable, Replenishable {
    protected final String tittle;
    protected double balance;

    protected Account(String tittle) {
        this.tittle = tittle;
    }

    public void changeBalance(double balance) {
        if (balance >= 0) {
            this.balance = balance;
        } else {
            System.out.printf("%sБаланс %s не может быть отрицательным!%s%n", Utils.ANSI_RED, tittle, Utils.ANSI_RESET);
        }
    }

    public void printBalance() {
        if (balance >= 0) {
            System.out.printf("%sБаланс %s равен %s $.%s%n", Utils.ANSI_GREEN, tittle, balance, Utils.ANSI_RESET);
        }
    }

    public String getTittle() {
        return tittle;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return tittle;
    }
}
