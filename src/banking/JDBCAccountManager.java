package banking;

import java.sql.*;
import java.util.Scanner;

public class JDBCAccountManager {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USER = "your_username"; // 오라클 계정
    private static final String PASS = "your_password";
    private Connection conn;
    private Scanner sc = new Scanner(System.in);

    public JDBCAccountManager() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Oracle DB 연결 성공!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 메뉴 출력
    public void showMenu() {
        System.out.println("===== JDBC 계좌관리 프로그램 =====");
        System.out.println("1. 계좌개설");
        System.out.println("2. 입금");
        System.out.println("3. 출금");
        System.out.println("4. 전체계좌조회");
        System.out.println("5. 지정계좌조회");
        System.out.println("6. 계좌삭제");
        System.out.println("7. 종료");
        System.out.print("선택: ");
    }

    // 계좌개설
    public void makeAccount() {
        try {
            System.out.print("계좌번호: "); String accNumber = sc.nextLine();
            System.out.print("고객이름: "); String name = sc.nextLine();
            System.out.print("잔액: "); int balance = Integer.parseInt(sc.nextLine());
            System.out.print("기본이자(%): "); int interest = Integer.parseInt(sc.nextLine());

            String sql = "INSERT INTO banking(idx, accNumber, name, balance, interest) " +
                         "VALUES(seq_banking_idx.NEXTVAL, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, accNumber);
            pstmt.setString(2, name);
            pstmt.setInt(3, balance);
            pstmt.setInt(4, interest);

            int result = pstmt.executeUpdate();
            if(result > 0) System.out.println("계좌 개설 완료!");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // 입금
    public void deposit() {
        try {
            System.out.print("계좌번호: "); String accNumber = sc.nextLine();
            System.out.print("입금액: "); int amount = Integer.parseInt(sc.nextLine());

            // 기존 잔액과 이자 가져오기
            String selectSql = "SELECT balance, interest FROM banking WHERE accNumber = ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectSql);
            selectStmt.setString(1, accNumber);
            ResultSet rs = selectStmt.executeQuery();

            if(rs.next()) {
                int balance = rs.getInt("balance");
                int interest = rs.getInt("interest");

                int interestMoney = (int)(balance * (interest / 100.0));
                int newBalance = balance + amount + interestMoney;

                String updateSql = "UPDATE banking SET balance = ? WHERE accNumber = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setInt(1, newBalance);
                updateStmt.setString(2, accNumber);
                updateStmt.executeUpdate();

                System.out.println("입금 완료! 새 잔액: " + newBalance);
            } else {
                System.out.println("계좌를 찾을 수 없습니다.");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // 출금
    public void withdraw() {
        try {
            System.out.print("계좌번호: "); String accNumber = sc.nextLine();
            System.out.print("출금액: "); int amount = Integer.parseInt(sc.nextLine());

            String selectSql = "SELECT balance FROM banking WHERE accNumber = ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectSql);
            selectStmt.setString(1, accNumber);
            ResultSet rs = selectStmt.executeQuery();

            if(rs.next()) {
                int balance = rs.getInt("balance");
                if(amount > balance) {
                    System.out.println("잔액이 부족합니다. 출금불가");
                } else {
                    String updateSql = "UPDATE banking SET balance = ? WHERE accNumber = ?";
                    PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                    updateStmt.setInt(1, balance - amount);
                    updateStmt.setString(2, accNumber);
                    updateStmt.executeUpdate();
                    System.out.println("출금 완료! 새 잔액: " + (balance - amount));
                }
            } else {
                System.out.println("계좌를 찾을 수 없습니다.");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // 전체 계좌 조회
    public void showAllAccounts() {
        try {
            String sql = "SELECT * FROM banking ORDER BY idx";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                System.out.println("-----------");
                System.out.println("계좌번호: " + rs.getString("accNumber"));
                System.out.println("이름: " + rs.getString("name"));
                System.out.println("잔액: " + rs.getInt("balance"));
                System.out.println("이자율: " + rs.getInt("interest") + "%");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // 지정 계좌 조회
    public void showAccount() {
        try {
            System.out.print("조회할 계좌번호: "); String accNumber = sc.nextLine();
            String sql = "SELECT * FROM banking WHERE accNumber = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, accNumber);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                System.out.println("계좌번호: " + rs.getString("accNumber"));
                System.out.println("이름: " + rs.getString("name"));
                System.out.println("잔액: " + rs.getInt("balance"));
                System.out.println("이자율: " + rs.getInt("interest") + "%");
            } else {
                System.out.println("계좌를 찾을 수 없습니다.");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // 계좌 삭제 (프로시저 호출)
    public void deleteAccount() {
        try {
            System.out.print("삭제할 계좌번호: "); String accNumber = sc.nextLine();
            CallableStatement cstmt = conn.prepareCall("{call DeleteAccount(?)}");
            cstmt.setString(1, accNumber);
            cstmt.execute();
            System.out.println("계좌 삭제 완료!");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // 메인 루프
    public void run() {
        while(true) {
            showMenu();
            int choice = Integer.parseInt(sc.nextLine());

            switch(choice) {
                case 1: makeAccount(); break;
                case 2: deposit(); break;
                case 3: withdraw(); break;
                case 4: showAllAccounts(); break;
                case 5: showAccount(); break;
                case 6: deleteAccount(); break;
                case 7: 
                    System.out.println("프로그램 종료");
                    try { conn.close(); } catch(Exception e) {}
                    System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        JDBCAccountManager manager = new JDBCAccountManager();
        manager.run();
    }
}
