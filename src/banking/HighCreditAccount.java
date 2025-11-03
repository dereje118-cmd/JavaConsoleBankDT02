package banking;

// HighCreditAccount: 신용 등급에 따라 추가 이자를 제공하는 계좌 클래스
// NormalAccount를 상속받아 기본 입출금 기능 + 신용등급 이자 기능 구현
public class HighCreditAccount extends NormalAccount {

    // 신용 등급(A, B, C)
    private char creditGrade;

    // 생성자: 계좌번호, 이름, 잔고, 기본이자, 신용등급 입력
    public HighCreditAccount(String accNumber, String name, int balance, int interest, char creditGrade) {
        super(accNumber, name, balance, interest); // 부모 생성자 호출
        this.creditGrade = creditGrade;           // 신용등급 저장
    }

    // 입금 시 이자 계산 포함
    @Override
    public void deposit(int amount) {
        // 부모 기본 이자율
        double baseRate = getInterest() / 100.0;
        double extraRate = 0.0; // 신용등급 추가 이자율 초기화

        // 신용 등급에 따라 추가 이자율 설정
        switch(creditGrade) {
            case 'A': extraRate = ICustomDefine.A; break;
            case 'B': extraRate = ICustomDefine.B; break;
            case 'C': extraRate = ICustomDefine.C; break;
        }

        // 총 이자 계산: (잔고 * (기본이자 + 추가이자))
        int interestMoney = (int)(getBalance() * (baseRate + extraRate));

        // 잔고 갱신: 기존 잔고 + 입금액 + 이자
        setBalance(getBalance() + amount + interestMoney);
    }

    // 계좌 정보 출력: 부모 정보 + 신용등급
    @Override
    public void showAccountInfo() {
        super.showAccountInfo();                  // 계좌번호, 이름, 잔액, 기본이자 출력
        System.out.println("신용등급: " + creditGrade); // 신용등급 추가 출력
    }
}
