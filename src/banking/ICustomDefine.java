package banking;

/**
 * 메뉴 번호, 계좌 종류, 신용등급 이자율 정의
 */
public interface ICustomDefine {
    // 메뉴
    int MAKE = 1;
    int DEPOSIT = 2;
    int WITHDRAW = 3;
    int INQUIRE = 4;
    int DELETE = 5;
    int SAVE_OPTION = 6;
    int EXIT = 7;

    // 계좌 종류
    int NORMAL = 1;
    int HIGHCREDIT = 2;

    // 신용등급 추가 이자율
    double A = 0.07;
    double B = 0.04;
    double C = 0.02;
	int SPECIAL = 0;
}
