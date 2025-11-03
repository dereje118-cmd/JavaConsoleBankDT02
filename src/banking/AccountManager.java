package banking;

import java.io.*;               // 파일 입출력 관련 클래스
import java.util.HashSet;       // 중복 없는 계좌 저장용 컬렉션
import java.util.Scanner;       // 사용자 입력
import java.util.Set;           // HashSet 인터페이스

// AccountManager: 계좌 관리(개설, 입금, 출금, 조회, 삭제, 저장) 기능을 담당
public class AccountManager {

    // 계좌 정보를 저장할 Set 컬렉션(HashSet 사용)
    private Set<Account> accSet = new HashSet<>();

    // 사용자 입력을 위한 Scanner
    private Scanner sc = new Scanner(System.in);

    // 자동저장 쓰레드
    private AutoSaver autoSaver;

    // 생성자: 프로그램 시작 시 기존 객체 파일(AccountInfo.obj)이 존재하면 불러오기
    public AccountManager() {
        try {
            File file = new File("AccountInfo.obj");
            if(file.exists()) {  // 파일이 존재하면
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                accSet = (Set<Account>) ois.readObject(); // 객체 읽기
                ois.close();
            }
        } catch (Exception e) {
            e.printStackTrace();  // 파일 읽기 실패 시 오류 출력
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
        int choice = sc.nextInt(); sc.nextLine(); // 계좌 종류 선택

        // 사용자 입력: 계좌번호, 고객이름, 초기잔고
        System.out.print("계좌번호: "); String accNum = sc.nextLine();
        System.out.print("고객이름: "); String name = sc.nextLine();
        System.out.print("잔고: "); int balance = sc.nextInt();

        if(choice == ICustomDefine.NORMAL) { // 보통계좌
            System.out.print("기본이자(%): "); int interest = sc.nextInt();
            NormalAccount na = new NormalAccount(accNum, name, balance, interest);
            accSet.add(na); // Set에 추가
        } else if(choice == ICustomDefine.HIGHCREDIT) { // 신용계좌
            System.out.print("기본이자(%): "); int interest = sc.nextInt();
            System.out.print("신용등급(A,B,C): "); char grade = sc.next().charAt(0);
            HighCreditAccount ha = new HighCreditAccount(accNum, name, balance, interest, grade);
            accSet.add(ha); // Set에 추가
        }
        System.out.println("계좌계설이 완료되었습니다.");
    }

    // 입금 기능
    public void depositMoney() {
        System.out.println("***입   금***");
        System.out.print("계좌번호: "); String accNum = sc.next();
        System.out.print("입금액: "); int amount = sc.nextInt();

        // Set에서 해당 계좌 번호 찾기
        for(Account acc : accSet) {
            if(acc.getAccNumber().equals(accNum)) {
                acc.deposit(amount);  // 계좌 유형별 입금(이자 계산 포함)
                System.out.println("입금이 완료되었습니다.");
                return;
            }
        }
        System.out.println("계좌번호를 찾을 수 없습니다.");
    }

    // 출금 기능
    public void withdrawMoney() {
        System.out.println("***출   금***");
        System.out.print("계좌번호: "); String accNum = sc.next();
        System.out.print("출금액: "); int amount = sc.nextInt();

        // Set에서 해당 계좌 번호 찾기
        for(Account acc : accSet) {
            if(acc.getAccNumber().equals(accNum)) {
                if(amount > acc.getBalance()) { // 잔액 부족
                    System.out.println("잔고가 부족합니다. 출금불가");
                } else {
                    acc.withdraw(amount); // 출금
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
            acc.showAccountInfo(); // 각 계좌 정보 출력
        }
        System.out.println("전체계좌정보 출력이 완료되었습니다.");
    }

    // 계좌 삭제
    public void deleteAccount() {
        System.out.println("***계좌정보삭제***");
        System.out.print("삭제할 계좌번호: ");
        String accNum = sc.next();
        // 해당 계좌번호가 일치하는 계좌 삭제
        accSet.removeIf(acc -> acc.getAccNumber().equals(accNum));
        System.out.println("삭제 완료.");
    }

    // 자동 저장 옵션
    public void saveOption() {
        if(autoSaver != null && autoSaver.isAlive()) {
            System.out.println("이미 자동저장이 실행중입니다.");
            return;
        }
        autoSaver = new AutoSaver(accSet); // AutoSaver 쓰레드 생성
        autoSaver.start();                 // 쓰레드 시작
        System.out.println("자동저장이 시작되었습니다.");
    }

    // 프로그램 종료 시 계좌 정보 저장
    public void saveOnExit() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("AccountInfo.obj"));
            oos.writeObject(accSet); // Set 전체를 파일로 저장
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
