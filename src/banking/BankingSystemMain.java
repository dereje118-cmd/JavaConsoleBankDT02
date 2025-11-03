package banking;

import java.util.Scanner;

// 프로그램 실행용 메인 클래스
public class BankingSystemMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);           // 사용자 입력용
        AccountManager manager = new AccountManager(); // 계좌 관리용 객체 생성

        while(true) {
            manager.showMenu(); // 메뉴 출력

            int choice = 0;
            try {
                // 사용자 입력을 문자열로 받아 정수 변환
                choice = Integer.parseInt(sc.nextLine());

                // 메뉴 선택 범위 벗어날 시 예외 발생
                if(choice < 1 || choice > 7) {
                    throw new MenuSelectException(); // 개발자 정의 예외
                }
            } catch(MenuSelectException e) {
                System.out.println(e.getMessage()); // 올바른 메뉴 안내
                continue; // 메뉴 선택으로 돌아감
            } catch(NumberFormatException e) {
                System.out.println("숫자를 입력해야 합니다."); // 문자를 입력했을 경우 안내
                continue; // 메뉴 선택으로 돌아감
            }

            // 선택된 메뉴에 따라 기능 실행
            switch(choice) {
                case ICustomDefine.MAKE:      // 1. 계좌개설
                    manager.makeAccount(); 
                    break;
                case ICustomDefine.DEPOSIT:   // 2. 입금
                    manager.depositMoney(); 
                    break;
                case ICustomDefine.WITHDRAW:  // 3. 출금
                    manager.withdrawMoney(); 
                    break;
                case ICustomDefine.INQUIRE:   // 4. 전체 계좌 정보 출력
                    manager.showAccInfo(); 
                    break;
                case ICustomDefine.DELETE:    // 5. 계좌 삭제
                    manager.deleteAccount(); 
                    break;
                case ICustomDefine.SAVE_OPTION: // 6. 자동저장 옵션
                    manager.saveOption(); 
                    break;
                case ICustomDefine.EXIT:      // 7. 프로그램 종료
                    manager.saveOnExit();      // 종료 전 계좌 정보 저장
                    System.out.println("프로그램을 종료합니다.");
                    sc.close();                // Scanner 닫기
                    System.exit(0);            // 프로그램 종료
            }
        }
    }
}
