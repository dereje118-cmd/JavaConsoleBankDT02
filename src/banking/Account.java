package banking;

import java.io.Serializable;

/**
 * Account 추상클래스
 * - 계좌의 기본 정보를 가지고 있음 (계좌번호, 고객이름, 잔액)
 * - 추상클래스로 정의하여 직접 인스턴스 생성 불가
 * - 상속을 통해 NormalAccount, HighCreditAccount, SpecialAccount 등 구체적인 계좌 구현
 * - Serializable 인터페이스 구현 → 파일 입출력 및 자동저장 가능
 */
public abstract class Account implements Serializable {
    private String accNumber; // 계좌번호
    private String name;      // 고객 이름
    private int balance;      // 계좌 잔액

    // 생성자
    public Account(String accNumber, String name, int balance) {
        this.accNumber = accNumber;
        this.name = name;
        this.balance = balance;
    }

    /**
     * 입금 기능은 계좌 종류마다 다르므로 추상 메서드로 정의
     */
    public abstract void deposit(int amount);

    /**
     * 출금 기능
     * @param amount 출금할 금액
     */
    public void withdraw(int amount) {
        balance -= amount;
    }

    /**
     * 계좌 정보 출력
     */
    public void showAccountInfo() {
        System.out.println("계좌번호: " + accNumber);
        System.out.println("고객이름: " + name);
        System.out.println("잔액: " + balance);
    }

    // Getter / Setter
    public String getAccNumber() { return accNumber; }
    public int getBalance() { return balance; }
    public void setBalance(int balance) { this.balance = balance; }

    /**
     * HashSet 중복 처리를 위한 equals() 재정의
     * 계좌번호가 동일하면 동일 계좌로 판단
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Account){
            return this.accNumber.equals(((Account)obj).getAccNumber());
        }
        return false;
    }

    /**
     * HashSet 중복 처리를 위한 hashCode() 재정의
     */
    @Override
    public int hashCode() {
        return accNumber.hashCode();
    }
}
