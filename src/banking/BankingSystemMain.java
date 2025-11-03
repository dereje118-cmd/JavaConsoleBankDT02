package banking;

import java.util.Scanner;

// BankingSystemMain: 프로그램 실행의 시작점(Main)
// 사용자와 상호작용하며 AccountManager를 통해 기능 호출
public class BankingSystemMain {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);        // 사용자 입력용 Scanner
        AccountManager manager = new AccountManager(); // 계좌 관리 클래스 생성

        // 프로그램 종료 전까지 반복 실행
        while(true) {
            manager.showMenu(); // 메뉴 출력
            int choice = 0;

            try {
                // 사용자 입력을 정수로 변환
                choice = Integer.parseInt(sc.nextLine());

                // 메뉴 범위를 벗어나면 개발자 정의 예외 발생
                if(choice < 1 || choice > 7) {
                    throw new MenuSelectException();
                }

            } catch(MenuSelectException e) {
                // 메뉴 선택 범위 오류 처리
                System.out.println(e.getMessage());
                continue; // 다음 반복으로 넘어감

            } catch(NumberFormatException e) {
                // 숫자가 아닌 입력 처리
                System.out.println("숫자를 입력해야 합니다.");
                continue; // 다음 반복으로 넘어감
            }

            // 메뉴 선택에 따라 AccountManager의 기능 호출
            switch(choice) {
                case ICustomDefine.MAKE:         // 1. 계좌 개설
                    manager.makeAccount(); 
                    break;
                case ICustomDefine.DEPOSIT:      // 2. 입금
                    manager.depositMoney(); 
                    break;
                case ICustomDefine.WITHDRAW:     // 3. 출금
                    manager.withdrawMoney(); 
                    break;
                case ICustomDefine.INQUIRE:      // 4. 계좌 정보 출력
                    manager.showAccInfo(); 
                    break;
                case ICustomDefine.DELETE:       // 5. 계좌 삭제
                    manager.deleteAccount(); 
                    break;
                case ICustomDefine.SAVE_OPTION:  // 6. 자동저장 옵션
                    manager.saveOption(); 
                    break;
                case ICustomDefine.EXIT:         // 7. 프로그램 종료
                    manager.saveOnExit();        // 종료 전 계좌 저장
                    System.out.println("프로그램을 종료합니다.");
                    sc.close();                  // Scanner 닫기
                    System.exit(0);              // 프로그램 종료
            }
        }
    }
}
