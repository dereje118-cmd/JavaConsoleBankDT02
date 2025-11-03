package banking;

import java.io.Serializable;

// Account는 모든 계좌의 최상위 추상 클래스입니다.
// 직접 객체 생성은 불가능하고, 상속을 통해서만 사용합니다.
// Serializable을 구현하여 객체를 파일로 저장하거나 불러올 수 있습니다.
public abstract class Account implements Serializable {

    // 계좌번호
    private String accNumber;

    // 고객 이름
    private String name;

    // 계좌 잔액
    private int balance;

    // 생성자: 계좌번호, 이름, 잔액을 초기화
    public Account(String accNumber, String name, int balance) {
        this.accNumber = accNumber;
        this.name = name;
        this.balance = balance;
    }

    // 입금 메서드는 계좌 유형에 따라 계산 방식이 달라질 수 있으므로
    // 추상 메서드로 정의
    public abstract void deposit(int amount);

    // 출금 메서드: 기본적으로 잔액에서 출금액을 차감
    public void withdraw(int amount) {
        balance -= amount;
    }

    // 계좌 정보를 출력하는 기본 메서드
    public void showAccountInfo() {
        System.out.println("계좌번호: " + accNumber);
        System.out.println("고객이름: " + name);
        System.out.println("잔액: " + balance);
    }

    // 계좌번호 getter
    public String getAccNumber() {
        return accNumber;
    }

    // 잔액 getter
    public int getBalance() {
        return balance;
    }

    // 잔액 setter: 입금/출금 시 사용
    public void setBalance(int balance) {
        this.balance = balance;
    }

    // HashSet 등 컬렉션에서 중복 계좌를 판단하기 위한 equals 오버라이딩
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Account) {
            Account acc = (Account) obj;
            // 계좌번호가 같으면 같은 계좌로 판단
            return this.accNumber.equals(acc.getAccNumber());
        }
        return false;
    }

    // HashSet/HashMap에서 효율적 비교를 위해 hashCode 오버라이딩
    @Override
    public int hashCode() {
        // 계좌번호 문자열의 해시값 사용
        return accNumber.hashCode();
    }
}
