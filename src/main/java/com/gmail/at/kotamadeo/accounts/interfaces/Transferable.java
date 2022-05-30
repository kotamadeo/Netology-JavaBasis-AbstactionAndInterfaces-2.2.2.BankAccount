package com.gmail.at.kotamadeo.accounts.interfaces;

import com.gmail.at.kotamadeo.accounts.classes.Account;

public interface Transferable {
    void transfer(Account account, double amount);
}
