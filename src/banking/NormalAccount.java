package banking;

/**
 * NormalAccount
 * - Account를 상속받아 기본 계좌 구현
 * - 입금시 기본 이자율 적용
 */
public class NormalAccount extends Account {
    private int interest; // 기본 이자율(%)

    public NormalAccount(String accNumber, String name, int balance, int interest) {
        super(accNumber, name, balance);
        this.interest = interest;
    }

    public int getInterest() { return interest; }

    /**
     * 입금 처리
     * 잔액 + 입금액 + (잔액*기본이자)
     */
    @Override
    public void deposit(int amount) {
        int interestMoney = (int)(getBalance() * (interest / 100.0));
        setBalance(getBalance() + amount + interestMoney);
    }

    /**
     * 계좌 정보 출력 시 기본 이자 표시
     */
    @Override
    public void showAccountInfo() {
        super.showAccountInfo();
        System.out.println("기본이자: " + interest + "%");
    }
}
