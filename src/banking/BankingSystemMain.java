package banking;

import java.util.Scanner;
import banking.ThreeByThreePuzzle;

public class BankingSystemMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AccountManager manager = new AccountManager();

        while(true) {
            // 메뉴 출력
            manager.showMenu();
            System.out.println("8. 3x3 퍼즐 게임"); // 퍼즐 메뉴 추가
            System.out.print("선택: ");

            int choice = 0;
            try {
                choice = Integer.parseInt(sc.nextLine());
                if(choice < 1 || choice > 8) {
                    throw new MenuSelectException();
                }
            } catch(MenuSelectException e) {
                System.out.println(e.getMessage());
                continue;
            } catch(NumberFormatException e) {
                System.out.println("숫자를 입력해야 합니다.");
                continue;
            }

            switch(choice) {
                case ICustomDefine.MAKE: manager.makeAccount(); break;
                case ICustomDefine.DEPOSIT: manager.depositMoney(); break;
                case ICustomDefine.WITHDRAW: manager.withdrawMoney(); break;
                case ICustomDefine.INQUIRE: manager.showAccInfo(); break;
                case ICustomDefine.DELETE: manager.deleteAccount(); break;
                case ICustomDefine.SAVE_OPTION: manager.saveOption(); break;
                case ICustomDefine.EXIT:
                    manager.saveOnExit();
                    System.out.println("프로그램을 종료합니다.");
                    sc.close();
                    System.exit(0);
                case 8:
                    // 3x3 퍼즐 실행
                    ThreeByThreePuzzle puzzle = new ThreeByThreePuzzle();
                    puzzle.shuffle(100); // 셔플 100회 이상
                    puzzle.play();
                    break;
            }
        }
    }
}
