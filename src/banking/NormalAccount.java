package banking;

// NormalAccount: 일반 보통계좌 클래스
// Account를 상속받아 입금 시 기본 이자를 적용
public class NormalAccount extends Account {

    // 기본 이자율(%)
    private int interest;

    // 생성자: 계좌번호, 이름, 잔고, 기본 이자 입력
    public NormalAccount(String accNumber, String name, int balance, int interest) {
        super(accNumber, name, balance); // 부모 클래스 생성자 호출
        this.interest = interest;        // 기본 이자율 저장
    }

    // 기본 이자율 반환 메서드
    public int getInterest() {
        return interest;
    }

    // 입금 시 잔고 + 기본 이자 계산
    @Override
    public void deposit(int amount) {
        // 기본 이자 계산: 잔고 * (interest / 100)
        int interestMoney = (int)(getBalance() * (interest / 100.0));

        // 잔고 갱신: 기존 잔고 + 입금액 + 이자
        setBalance(getBalance() + amount + interestMoney);
    }

    // 계좌 정보 출력: 부모 정보 + 기본 이자
    @Override
    public void showAccountInfo() {
        super.showAccountInfo();                  // 계좌번호, 이름, 잔액 출력
        System.out.println("기본이자: " + interest + "%"); // 기본 이자 출력
    }
}
