package banking;

import java.io.Serializable;

// 계좌 최상위 클래스(추상 클래스)
// 인스턴스 생성 불가, 상속 목적용
public abstract class Account implements Serializable {
    private String accNumber; // 계좌번호
    private String name;      // 고객이름
    private int balance;      // 잔액

    public Account(String accNumber, String name, int balance) {
        this.accNumber = accNumber;
        this.name = name;
        this.balance = balance;
    }

    // 입금은 각 계좌 타입에 따라 다르게 처리
    public abstract void deposit(int amount);

    // 출금 기본 기능
    public void withdraw(int amount) {
        balance -= amount;
    }

    // 계좌 정보 출력
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

    // HashSet에서 계좌 중복 판단
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
