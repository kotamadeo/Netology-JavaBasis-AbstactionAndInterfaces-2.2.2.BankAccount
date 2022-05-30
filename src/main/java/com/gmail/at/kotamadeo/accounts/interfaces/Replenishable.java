package com.gmail.at.kotamadeo.accounts.interfaces;

import com.gmail.at.kotamadeo.wallet.Wallet;

public interface Replenishable {
    void replenish(Wallet wallet, double amount);
}
