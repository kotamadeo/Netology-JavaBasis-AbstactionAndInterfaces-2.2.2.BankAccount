package com.gmail.at.kotamadeo.accounts.interfaces;

import com.gmail.at.kotamadeo.bills.Bill;

public interface Payable {
    void pay(Bill Bill, double amount);
}
