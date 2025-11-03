package banking;

// 신용신뢰계좌, NormalAccount 상속
public class HighCreditAccount extends NormalAccount {
    private char creditGrade; // 신용등급 A,B,C

    public HighCreditAccount(String accNumber, String name, int balance, int interest, char creditGrade) {
        super(accNumber, name, balance, interest);
        this.creditGrade = creditGrade;
    }

    @Override
    public void deposit(int amount) {
        // 기본 이자
        double baseRate = getInterest() / 100.0;
        double extraRate = 0.0;

        // 신용등급에 따른 추가 이자
        switch(creditGrade) {
            case 'A': extraRate = ICustomDefine.A; break;
            case 'B': extraRate = ICustomDefine.B; break;
            case 'C': extraRate = ICustomDefine.C; break;
        }

        int interestMoney = (int)(getBalance() * (baseRate + extraRate));
        setBalance(getBalance() + amount + interestMoney);
    }

    @Override
    public void showAccountInfo() {
        super.showAccountInfo();
        System.out.println("신용등급: " + creditGrade);
    }
}
