# **Задача № 2 Банковские счета**

## **Цель**:
Часто в проектировании программ нам удобно опираться на понятия, которые не представлены в реальном мире, но служат удобной "опорой" для объединения нескольких классов.

Так, например, в банковском деле нет абстрактного понятия "Счет". Каждый счет в банке имеет четкое назначение: сберегательный, кредитный, расчетный. Но банковская программа работает с общими для счетов операциями как с одинаковыми объектами, и выполняет их, обращаясь к общему типу "Счет", хотя его и невозможно явно инстанцировать в программе. Реализуйте этот сценарий, опираясь на механизмы полиморфизма.
1. Создайте несколько классов — различных счетов на основе общего интерфейса\класса: 
* **SavingsAccount** - сберегательный  счет;
* **CreditAccount** - кредитный счет;
* **Расчетный счет** - расчетный счет.
2. Выполните перевод с одного счета на другой.


### *Пример*:

``` Пример 1
1. Создайте абстрактный класс Account с тремя методами: заплатить, перевести, пополнить (pay(int amount), transfer(Account account, int amount), addMoney(int amount)). Платеж в нашем случае будет выглядеть просто как списание средств.
2. Добавьте классы Сберегательный, Кредитный, Расчетный (SavingsAccount, CreditAccount, PaymentAccount соответственно) как потомков класса Счет. В них нужно переопределить методы базового класса. Каждый из них должен хранить баланс. Со сберегательного счета нельзя платить, только переводить и пополнять. Также сберегательный не может уходить в минус. Кредитный не может иметь положительный баланс – если платить с него, то уходит в минус, чтобы вернуть в 0, надо пополнить его. Расчетный может выполнять все три операции, но не может уходить в минус.
3. Продемонстрируйте работу счетов. Создайте три переменные типа Account и присвойте им три разных типа счетов.
```

### **Моя реализация**:

1. Реализация осуществлена в парадигме ООП.
2. Создал структуру классов и интерфейсов:

* **Program** - класс, отвечающий за запуск программы, путем инициирования метода *start()* с инициированием внутри себя
  вспомогательных ```void``` методов: 
  * *printMenu()* - выводит меню команд программы на экран; 
  * *printAccounts()* - выводит список счетов; 

#### Класс **Program**:
``` java
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
```
* **Transferable** - интерфейс, описывающий возможность перевода денежных средств со счета на счет имеет ```abstract``` метод: *transfer()* - позволяет перевести денежные средства с одного счета на другой;
* **Payable** - интерфейс, описывающий возможность оплаты какого-либо счета имеет ```abstract``` метод: *pay()* - позволяет оплатить со счета что-либо;
* **Replenishable** - интерфейс, описывающий возможность пополнить счет с кошелька имеет ```abstract``` метод: *replenish()* - позволяет пополнить счет с кошелька;
* **Account** - абстрактный класс, задающий общий логику работы счетов, имплементирующий вышеуказанные интерфейсы и имеющий ```void``` методы: *changeBalance()* - позволяющий менять значение баланса, *printBalance()* - выводящий баланс на экран, также имеет переопределенный *toString()*.

#### Класс **Account**:
``` java   
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
```

* **Bill** - класс, описывающий счет, например, налоговую декларацию. Имеет ```void``` метод *setAmount()* - позволяющий изменить значение счета.

#### Класс **Bill**:
``` java   
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
```

* **Wallet** - класс, описывающий кошелек. Имеет ```void``` метод: *setBalance()* - позволяющий, изменить баланс кошелька.
```java
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
```

2. Использовал кодирование цвета текста (ANSI).

#### Класс **Utils**:
``` java
public class Utils {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void printDelim() {
        System.out.println(ANSI_GREEN + "*********************************************" + ANSI_RESET);
    }
}
```

3. Использовал ```try-catch```, чтобы избежать падение программы в исключения.
4. Реализовал расчеты в методах через ```BigDecimal``` для корректной работы с деньгами. 

#### Метод *main()* в классе **Main**:
``` java
public class Main {
    public static void main(String[] args) {
        var program = new Program();
        program.start();
    }
}
```

## *Вывод в консоль*:

* меню:
``` 
Эта программа эмулирует работу библиотеки.
Возможные команды:
0 или выход: выход из программы.
1: добавить книгу.
2: добавить читателя.
3: добавить поставщика.
4: добавить библиотекаря.
5: добавить администратора.
6: читатели читают.
7: читатель берет книгу у библиотекаря.
8: читатель возвращает книгу библиотекарю.
9: библиотекарь ищет книгу для читателя.
10: библиотекарь дает книгу читателю.
11: администратор уведомляет читателя о просроченности аренды книги.
12: добавить книги для заказа.
13: библиотекарь предоставляет список книг для заказа администратору.
14: администратор заказывает книги у поставщика.
15: поставщик поставляет книги администратору.
16: демонстрация работы всех методов.
>>>>
```

* Демонстрация работы:
```
Введите начальный баланс вашего кошелька и сумму налоговой декларации в $. через пробел
5000 10000

1
Выберите счет и введите сумму в долларах, чтобы задать баланс счетов через пробел:
1. сберегательный счет.
2. расчетный счет.
3. кредитный счет.
3 -5000
Баланс кредитный счет равен -5000.0 $.

2
Выберите счет с которого и на какой хотите перевести денежные средства и введите сумму в $ через пробел:
1. сберегательный счет.
2. расчетный счет.
3. кредитный счет.
3 1 10000
Перевод денежных средств в размере 10000.0 $ с кредитный счет на сберегательный счет успешно завершен!
Текущий баланс: -15000.0 $.

3
Выберите счет с которого вы хотите оплатить налоговые декларации и введите сумму в $:
1. сберегательный счет.
2. расчетный счет.
3. кредитный счет.
3 10000
С кредитный счет осуществлена успешная оплата услуги в размере 10000.0 $. Текущая задолженость составляет 0.0 $.
Баланс кредитный счет равен -25000.0 $.

4
Выберите счет и введите сумму в $ через пробел, чтобы пополнить его:
1. сберегательный счет.
2. расчетный счет.
3. кредитный счет.
3 10000
Ошибка пополнения! На вашем кошельке не хватает 5000 $.
Баланс кредитный счет равен -25000.0 $.

5
Баланс сберегательный счет равен 10000.0 $.
Баланс расчетный счет равен 0.0 $.
Баланс кредитный счет равен -25000.0 $.
Баланс вашего кошелька равен 5000.0 $.
```