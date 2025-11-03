package banking;

// ICustomDefine: 인터페이스를 활용한 상수 정의
// 프로그램 내 메뉴 선택 및 신용 등급 이자율, 계좌 종류를 상수로 관리
public interface ICustomDefine {

    // ===== 메뉴 선택 상수 =====
    int MAKE        = 1; // 1. 계좌 개설
    int DEPOSIT     = 2; // 2. 입금
    int WITHDRAW    = 3; // 3. 출금
    int INQUIRE     = 4; // 4. 전체 계좌 정보 출력
    int DELETE      = 5; // 5. 계좌 삭제
    int SAVE_OPTION = 6; // 6. 자동 저장 옵션
    int EXIT        = 7; // 7. 프로그램 종료

    // ===== 계좌 종류 상수 =====
    int NORMAL      = 1; // 1. 보통계좌
    int HIGHCREDIT  = 2; // 2. 신용신뢰계좌

    // ===== 신용 등급별 추가 이자율 =====
    double A = 0.07; // 등급 A: 7% 추가 이자
    double B = 0.04; // 등급 B: 4% 추가 이자
    double C = 0.02; // 등급 C: 2% 추가 이자
}
