package banking;

// 프로그램 상수 정의용 인터페이스
public interface ICustomDefine {
    // 메뉴 선택 상수
    int MAKE = 1;        // 계좌개설
    int DEPOSIT = 2;     // 입금
    int WITHDRAW = 3;    // 출금
    int INQUIRE = 4;     // 계좌정보 출력
    int DELETE = 5;      // 계좌삭제
    int SAVE_OPTION = 6; // 자동저장 옵션
    int EXIT = 7;        // 프로그램 종료

    // 계좌 유형
    int NORMAL = 1;      // 보통계좌
    int HIGHCREDIT = 2;  // 신용신뢰계좌

    // 신용 등급별 추가 이자율
    double A = 0.07;
    double B = 0.04;
    double C = 0.02;
}
