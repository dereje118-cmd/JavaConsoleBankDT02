package banking;

/**
 * HighCreditAccount
 * - NormalAccount 상속
 * - 신용등급(A,B,C)에 따라 추가 이자 지급
 */
public class HighCreditAccount extends NormalAccount {
    private char creditGrade; // 고객 신용등급

    public HighCreditAccount(String accNumber, String name, int balance, int interest, char creditGrade) {
        super(accNumber, name, balance, interest);
        this.creditGrade = creditGrade;
    }

    /**
     * 입금 처리
     * 기본이자 + 신용등급 추가 이자
     */
    @Override
    public void deposit(int amount) {
        double baseRate = getInterest() / 100.0; // 기본이자
        double extraRate = 0.0; // 추가 이자 초기화

        switch(creditGrade){
            case 'A': extraRate = ICustomDefine.A; break;
            case 'B': extraRate = ICustomDefine.B; break;
            case 'C': extraRate = ICustomDefine.C; break;
        }

        int interestMoney = (int)(getBalance() * (baseRate + extraRate));
        setBalance(getBalance() + amount + interestMoney);
    }

    /**
     * 계좌 정보 출력 시 신용등급 포함
     */
    @Override
    public void showAccountInfo() {
        super.showAccountInfo();
        System.out.println("신용등급: " + creditGrade);
    }
}
