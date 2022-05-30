package com.gmail.at.kotamadeo.accounts.classes;

import com.gmail.at.kotamadeo.bills.Bill;
import com.gmail.at.kotamadeo.utils.Utils;
import com.gmail.at.kotamadeo.wallet.Wallet;

import java.math.BigDecimal;

public class CreditAccount extends Account {
    private static final int OVER_DRAFT = -100_000;

    public CreditAccount(String tittle) {
        super(tittle);
    }

    @Override
    public void pay(Bill bill, double amount) {
        if (bill.getAmount() >= amount) {
            var newBalanceOperation = BigDecimal.valueOf(balance).subtract(BigDecimal.valueOf(amount));
            if (newBalanceOperation.doubleValue() >= OVER_DRAFT) {
                var NewBillAmountOperation = BigDecimal.valueOf(bill.getAmount()).subtract(BigDecimal.valueOf(amount));
                bill.setAmount(NewBillAmountOperation.doubleValue());
                changeBalance(newBalanceOperation.doubleValue());
                System.out.printf("%sС %s осуществлена успешная оплата %s в размере %s$. Текущая задолженость " +
                                "составляет %s$.%s%n",
                        Utils.ANSI_GREEN, tittle, bill.getTittle(), amount, bill.getAmount(), Utils.ANSI_RESET);
                printBalance();
            } else {
                System.out.printf("%sОшибка! Овердрафт %s в размере %s$ превышен!%s%n",
                        Utils.ANSI_RED, tittle, OVER_DRAFT, Utils.ANSI_RESET);
            }
        } else {
            System.out.printf("%sВсе %s оплачены!Текущая задолженость составляет %s$.%s%n",
                    Utils.ANSI_GREEN, bill.getTittle(), bill.getAmount(), Utils.ANSI_RESET);
        }
    }

    @Override
    public void replenish(Wallet wallet, double amount) {
        if (wallet.getBalance() >= amount) {
            if (balance <= 0.0) {
                var newBalanceOperation = BigDecimal.valueOf(balance).add(BigDecimal.valueOf(amount));
                var newWalletBalance = BigDecimal.valueOf(wallet.getBalance()).subtract(BigDecimal.valueOf(amount));
                wallet.setBalance(newWalletBalance.doubleValue());
                changeBalance(newBalanceOperation.doubleValue());
                System.out.printf("%s%s успешно пополнен на сумму: %s$.%s%n", Utils.ANSI_GREEN, tittle, amount,
                        Utils.ANSI_RESET);
                System.out.println("Текущий баланс кошелька: " + wallet.getBalance() + "$.");
                printBalance();
            } else {
                System.out.println(Utils.ANSI_RED + "Ошибка пополнения! Кредитный счет не может иметь положительный " +
                        "баланс." + Utils.ANSI_RESET);
            }
        } else {
            var result = BigDecimal.valueOf(wallet.getBalance()).subtract(BigDecimal.valueOf(amount));
            System.out.printf("%sОшибка пополнения! На вашем кошельке не хватает %s$.%s%n",
                    Utils.ANSI_RED, result.abs(), Utils.ANSI_RESET);
            printBalance();
        }
    }

    @Override
    public void transfer(Account account, double amount) {
        if (balance - amount >= OVER_DRAFT) {
            var newAccountBalanceOperation = BigDecimal.valueOf(account.getBalance()).add(BigDecimal.valueOf(amount));
            var newBalanceOperation = BigDecimal.valueOf(balance).subtract(BigDecimal.valueOf(amount));
            account.changeBalance(newAccountBalanceOperation.doubleValue());
            changeBalance(newBalanceOperation.doubleValue());
            System.out.printf("%sПеревод денежных средств в размере %s$ с %s на %s успешно завершен!%s%n",
                    Utils.ANSI_GREEN, amount, tittle, account.getTittle(), Utils.ANSI_RESET);
            System.out.println("Текущий баланс: " + balance + "$.");
        } else {
            System.out.printf("%sОшибка перевода денежных средств на %s! Овердрафт %s в размере %s$ превышен!%s%n",
                    Utils.ANSI_RED, account.getTittle(), tittle, OVER_DRAFT, Utils.ANSI_RESET);
            printBalance();
        }
    }

    @Override
    public void changeBalance(double input) {
        if (input >= OVER_DRAFT && balance <= 0.0) {
            balance = input;
        } else {
            System.out.printf("%sОшибка! баланс %s не может быть быть положительным или быть больше овердрафта в " +
                    "размере %s $.%s%n", Utils.ANSI_RED, tittle, OVER_DRAFT, Utils.ANSI_RESET);
        }
    }

    @Override
    public void printBalance() {
        if (balance <= 0.0 && balance >= OVER_DRAFT) {
            System.out.printf("%sБаланс %s равен %s $.%s%n", Utils.ANSI_GREEN, tittle, balance, Utils.ANSI_RESET);
        } else {
            System.out.printf("%sБаланс %s не может быть пололожительным или больше овердрафта в размере %s$!%s%n",
                    Utils.ANSI_RED, tittle, OVER_DRAFT, Utils.ANSI_RESET);
        }
    }
}
