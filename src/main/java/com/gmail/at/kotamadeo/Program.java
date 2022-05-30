package com.gmail.at.kotamadeo;

import com.gmail.at.kotamadeo.accounts.classes.Account;
import com.gmail.at.kotamadeo.accounts.classes.CreditAccount;
import com.gmail.at.kotamadeo.accounts.classes.PaymentAccount;
import com.gmail.at.kotamadeo.accounts.classes.SavingsAccount;
import com.gmail.at.kotamadeo.bills.Bill;
import com.gmail.at.kotamadeo.utils.Utils;
import com.gmail.at.kotamadeo.wallet.Wallet;

import java.util.Scanner;

public class Program {
    private final Scanner scanner = new Scanner(System.in);
    private final Wallet wallet = new Wallet();
    private final Bill bill = new Bill("услуги");
    private final Account[] ACCOUNTS = {
            new SavingsAccount("сберегательный счет"),
            new PaymentAccount("расчетный счет"),
            new CreditAccount("кредитный счет")};

    public void start() {
        try {
            String[] allInput;
            String input;
            System.out.println(Utils.ANSI_BLUE + "Введите начальный баланс вашего кошелька и сумму налоговой " +
                    "декларации в $. через пробел" + Utils.ANSI_RESET);
            allInput = scanner.nextLine().split(" ");
            wallet.setBalance(Double.parseDouble(allInput[0]));
            bill.setAmount(Double.parseDouble(allInput[1]));
            while (true) {
                try {
                    printMenu();
                    input = scanner.nextLine();
                    if ("выход".equalsIgnoreCase(input) || "0".equals(input)) {
                        scanner.close();
                        break;
                    } else {
                        var operationNumber = Integer.parseInt(input);
                        switch (operationNumber) {
                            case 1:
                                System.out.println(Utils.ANSI_BLUE + "Выберите счет и введите сумму в долларах, " +
                                        "чтобы задать баланс счетов через пробел:" + Utils.ANSI_RESET);
                                printAccounts(ACCOUNTS);
                                allInput = scanner.nextLine().split(" ");
                                ACCOUNTS[Integer.parseInt(allInput[0]) - 1].changeBalance(Double.
                                        parseDouble(allInput[1]));
                                ACCOUNTS[Integer.parseInt(allInput[0]) - 1].printBalance();
                                break;
                            case 2:
                                System.out.println(Utils.ANSI_BLUE + "Выберите счет с которого и на какой хотите " +
                                        "перевести денежные средства и введите сумму в $ через пробел:" +
                                        Utils.ANSI_RESET);
                                printAccounts(ACCOUNTS);
                                allInput = scanner.nextLine().split(" ");
                                ACCOUNTS[Integer.parseInt(allInput[0]) - 1].transfer(ACCOUNTS[Integer.
                                        parseInt(allInput[1]) - 1], Double.parseDouble(allInput[2]));
                                break;
                            case 3:
                                System.out.println(Utils.ANSI_BLUE + "Выберите счет с которого вы хотите " +
                                        "оплатить налоговые декларации и введите сумму в $:" + Utils.ANSI_RESET);
                                printAccounts(ACCOUNTS);
                                allInput = scanner.nextLine().split(" ");
                                ACCOUNTS[Integer.parseInt(allInput[0]) - 1].pay(bill,
                                        Double.parseDouble(allInput[1]));
                                break;
                            case 4:
                                System.out.println(Utils.ANSI_BLUE + "Выберите счет и введите сумму в $ через " +
                                        "пробел, чтобы пополнить его:" + Utils.ANSI_RESET);
                                printAccounts(ACCOUNTS);
                                allInput = scanner.nextLine().split(" ");
                                ACCOUNTS[Integer.parseInt(allInput[0]) - 1].replenish(wallet,
                                        Double.parseDouble(allInput[1]));
                                break;
                            case 5:
                                for (Account account : ACCOUNTS) {
                                    account.printBalance();
                                }
                                wallet.printBalance();
                                break;
                            default:
                                System.out.println(Utils.ANSI_RED + "Неверный номер операции!" + Utils.ANSI_RESET);
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println(Utils.ANSI_RED + "Ошибка ввода!" + Utils.ANSI_RESET);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println(Utils.ANSI_RED + "неверный ввод баланса кошелька и/или суммы налоговой декларации!"
                    + Utils.ANSI_RESET);
        }
    }

    private static void printMenu() {
        System.out.println(Utils.ANSI_YELLOW + "Эта программа эмулирует работу банковского приложения со счетами!" +
                Utils.ANSI_RESET);
        System.out.println(Utils.ANSI_PURPLE + "Возможные команды программы:" + Utils.ANSI_RESET);
        System.out.println("0 или выход: выход из программы.");
        System.out.println("1: задать начальный баланс счетов");
        System.out.println("2: сделать перевод со счета на счет:");
        System.out.println("3: оплатить налоговую декларацию со счета:");
        System.out.println("4: пополнить счет с кошелька:");
        System.out.println("5: вывести баланс счетов:");
    }

    private static void printAccounts(Account[] accounts) {
        for (var i = 0; i < accounts.length; i++) {
            System.out.printf("%s%s. %s.%s%n", Utils.ANSI_PURPLE, i + 1, accounts[i], Utils.ANSI_RESET);
        }
    }
}
