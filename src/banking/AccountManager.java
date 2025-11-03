package banking;

import java.io.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

// 계좌 관련 모든 기능 수행
public class AccountManager {
    private Set<Account> accSet = new HashSet<>(); // HashSet으로 계좌 관리
    private Scanner sc = new Scanner(System.in);   // 입력용
    private AutoSaver autoSaver;                   // 자동저장 쓰레드

    // 생성자: 프로그램 시작시 obj파일이 있으면 불러오기
    public AccountManager() {
        try {
            File file = new File("AccountInfo.obj");
            if(file.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                accSet = (Set<Account>) ois.readObject();
                ois.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 메뉴 출력
    public void showMenu() {
        System.out.println("-----Menu------");
        System.out.println("1.계좌개설");
        System.out.println("2.입금");
        System.out.println("3.출금");
        System.out.println("4.계좌정보출력");
        System.out.println("5.계좌정보삭제");
        System.out.println("6.저장옵션");
        System.out.println("7.프로그램종료");
        System.out.print("선택: ");
    }

    // 계좌 개설
    public void makeAccount() {
        System.out.println("***신규계좌개설***");
        System.out.println("1.보통계좌 2.신용신뢰계좌");
        int choice = sc.nextInt(); sc.nextLine();

        System.out.print("계좌번호: "); String accNum = sc.nextLine();
        System.out.print("고객이름: "); String name = sc.nextLine();
        System.out.print("잔고: "); int balance = sc.nextInt();

        if(choice == ICustomDefine.NORMAL) {
            System.out.print("기본이자(%): "); int interest = sc.nextInt();
            NormalAccount na = new NormalAccount(accNum, name, balance, interest);
            accSet.add(na);
        } else if(choice == ICustomDefine.HIGHCREDIT) {
            System.out.print("기본이자(%): "); int interest = sc.nextInt();
            System.out.print("신용등급(A,B,C): "); char grade = sc.next().charAt(0);
            HighCreditAccount ha = new HighCreditAccount(accNum, name, balance, interest, grade);
            accSet.add(ha);
        }
        System.out.println("계좌계설이 완료되었습니다.");
    }

    // 입금
    public void depositMoney() {
        System.out.println("***입   금***");
        System.out.print("계좌번호: "); String accNum = sc.next();
        System.out.print("입금액: "); int amount = sc.nextInt();

        // 음수, 500원 단위 체크
        if(amount < 0) {
            System.out.println("입금액은 음수일 수 없습니다.");
            return;
        }
        if(amount % 500 != 0) {
            System.out.println("입금액은 500원 단위로만 가능합니다.");
            return;
        }

        for(Account acc : accSet) {
            if(acc.getAccNumber().equals(accNum)) {
                acc.deposit(amount);
                System.out.println("입금이 완료되었습니다.");
                return;
            }
        }
        System.out.println("계좌번호를 찾을 수 없습니다.");
    }

    // 출금
    public void withdrawMoney() {
        System.out.println("***출   금***");
        System.out.print("계좌번호: "); String accNum = sc.next();
        System.out.print("출금액: "); int amount = sc.nextInt();

        // 음수, 1000원 단위 체크
        if(amount < 0) {
            System.out.println("출금액은 음수일 수 없습니다.");
            return;
        }
        if(amount % 1000 != 0) {
            System.out.println("출금액은 1000원 단위로만 가능합니다.");
            return;
        }

        for(Account acc : accSet) {
            if(acc.getAccNumber().equals(accNum)) {
                if(amount > acc.getBalance()) {
                    System.out.println("잔고가 부족합니다. 금액전체를 출금할까요? (Y/N)");
                    char choice = sc.next().charAt(0);
                    if(choice == 'Y' || choice == 'y') {
                        acc.withdraw(acc.getBalance());
                        System.out.println("전체 출금 완료되었습니다.");
                    } else {
                        System.out.println("출금 요청 취소되었습니다.");
                    }
                } else {
                    acc.withdraw(amount);
                    System.out.println("출금이 완료되었습니다.");
                }
                return;
            }
        }
        System.out.println("계좌번호를 찾을 수 없습니다.");
    }

    // 전체 계좌 정보 출력
    public void showAccInfo() {
        System.out.println("***계좌정보출력***");
        for(Account acc : accSet) {
            System.out.println("-------------");
            acc.showAccountInfo();
        }
        System.out.println("전체계좌정보 출력이 완료되었습니다.");
    }

    // 계좌 삭제
    public void deleteAccount() {
        System.out.println("***계좌정보삭제***");
        System.out.print("삭제할 계좌번호: ");
        String accNum = sc.next();
        accSet.removeIf(acc -> acc.getAccNumber().equals(accNum));
        System.out.println("삭제 완료.");
    }

    // 자동저장 옵션 실행
    public void saveOption() {
        if(autoSaver != null && autoSaver.isAlive()) {
            System.out.println("이미 자동저장이 실행중입니다.");
            return;
        }
        autoSaver = new AutoSaver(accSet);
        autoSaver.start();
        System.out.println("자동저장이 시작되었습니다.");
    }

    // 프로그램 종료 시 저장
    public void saveOnExit() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("AccountInfo.obj"));
            oos.writeObject(accSet);
            oos.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

        // 자동저장 쓰레드 종료
        if(autoSaver != null && autoSaver.isAlive()) {
            autoSaver.stopAutoSave();
        }
    }
}
