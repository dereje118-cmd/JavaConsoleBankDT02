package banking;

import java.io.Serializable;

public abstract class Account implements Serializable {
    private String accNumber;
    private String name;
    private int balance;

    public Account(String accNumber, String name, int balance) {
        this.accNumber = accNumber;
        this.name = name;
        this.balance = balance;
    }

    public abstract void deposit(int amount);

    public void withdraw(int amount) {
        balance -= amount;
    }

    public void showAccountInfo() {
        System.out.println("계좌번호: " + accNumber);
        System.out.println("고객이름: " + name);
        System.out.println("잔액: " + balance);
    }

    public String getAccNumber() {
        return accNumber;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Account) {
            Account acc = (Account) obj;
            return this.accNumber.equals(acc.getAccNumber());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return accNumber.hashCode();
    }
}
