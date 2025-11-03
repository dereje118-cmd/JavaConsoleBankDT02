package banking;

/**
 * SpecialAccount
 * - NormalAccount 상속
 * - 입금 시 짝수번째 입금에 500원 축하금 지급
 * - 입금 횟수 기록
 */
public class SpecialAccount extends NormalAccount {
    private int depositCount = 0; // 입금 횟수

    public SpecialAccount(String accNumber, String name, int balance, int interest) {
        super(accNumber, name, balance, interest);
    }

    /**
     * 입금 처리
     * 홀수회차: NormalAccount와 동일
     * 짝수회차: NormalAccount + 500원 보너스
     */
    @Override
    public void deposit(int amount) {
        depositCount++;
        int interestMoney = (int)(getBalance() * (getInterest() / 100.0));
        int bonus = (depositCount % 2 == 0) ? 500 : 0; // 짝수회차 축하금
        setBalance(getBalance() + amount + interestMoney + bonus);
    }

    /**
     * 계좌 정보 출력 시 입금 횟수 표시
     */
    @Override
    public void showAccountInfo() {
        super.showAccountInfo();
        System.out.println("입금횟수: " + depositCount);
    }
}
