package banking;

public class NormalAccount extends Account {
    private int interest; // 기본이자율

    public NormalAccount(String accNumber, String name, int balance, int interest) {
        super(accNumber, name, balance);
        this.interest = interest;
    }

    public int getInterest() {
        return interest;
    }

    @Override
    public void deposit(int amount) {
        int interestMoney = (int)(getBalance() * (interest / 100.0));
        setBalance(getBalance() + amount + interestMoney);
    }

    @Override
    public void showAccountInfo() {
        super.showAccountInfo();
        System.out.println("기본이자: " + interest + "%");
    }
}
