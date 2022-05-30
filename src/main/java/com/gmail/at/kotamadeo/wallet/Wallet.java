package com.gmail.at.kotamadeo.wallet;

import com.gmail.at.kotamadeo.utils.Utils;

public class Wallet {
    public static double balance;

    public void setBalance(double balance) {
        if (balance >= 0) {
            Wallet.balance = balance;
        } else {
            System.out.printf("%sБаланс вашего кошелька не может быть отрицательным.%s%n", Utils.ANSI_RED,
                    Utils.ANSI_RESET);
        }
    }

    public double getBalance() {
        return balance;
    }

    public void printBalance() {
        if (balance >= 0.00) {
            System.out.printf("%sБаланс вашего кошелька равен %s $.%s%n", Utils.ANSI_GREEN, balance, Utils.ANSI_RESET);
        }
    }
}
