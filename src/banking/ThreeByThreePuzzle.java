package banking.threeby3;

import java.util.Scanner;
import java.util.Random;

/**
 * 3x3 퍼즐 게임
 * - x가 빈 공간
 * - a,d,w,s 입력으로 블록 이동
 * - 퍼즐 완성 시 재시작 여부 확인
 */
public class ThreeByThreePuzzle {
    private int[][] board = new int[3][3];
    private int emptyRow = 2, emptyCol = 2;
    private Scanner sc = new Scanner(System.in);

    public ThreeByThreePuzzle() { resetBoard(); }

    // 셔플
    public void shuffle(int moves) {
        Random rand = new Random();
        String[] directions = {"w","a","s","d"};
        for(int i=0;i<moves;i++){
            move(directions[rand.nextInt(4)]);
        }
    }

    // 게임 진행
    public void play() {
        while(true){
            printBoard();
            System.out.print("이동키(a:좌,d:우,w:위,s:아래,q:종료): ");
            String input = sc.nextLine().toLowerCase();
            if(input.equals("q")) break;
            if(!move(input)) System.out.println("이동 불가!");

            if(isComplete()){
                printBoard();
                System.out.println("퍼즐 완료!");
                System.out.print("재시작? (y/n): ");
                String restart = sc.nextLine();
                if(restart.equalsIgnoreCase("y")){
                    resetBoard();
                    shuffle(100); // 100회 셔플
                } else break;
            }
        }
    }

    private boolean move(String dir){
        int newRow = emptyRow, newCol = emptyCol;
        switch(dir){
            case "w": newRow++; break;
            case "s": newRow--; break;
            case "a": newCol++; break;
            case "d": newCol--; break;
            default: return false;
        }
        if(newRow<0||newRow>2||newCol<0||newCol>2) return false;
        board[emptyRow][emptyCol] = board[newRow][newCol];
        board[newRow][newCol] = 0;
        emptyRow=newRow; emptyCol=newCol;
        return true;
    }

    private boolean isComplete(){
        int num=1;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(i==2 && j==2) return true;
                if(board[i][j]!=num++) return false;
            }
        }
        return true;
    }

    private void printBoard(){
        System.out.println("----- 3x3 퍼즐 -----");
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                System.out.print(board[i][j]==0?" x ":" "+board[i][j]);
            }
            System.out.println();
        }
    }

    private void resetBoard(){
        int num=1;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                board[i][j] = (i==2 && j==2)? 0 : num++;
            }
        }
        emptyRow=2; emptyCol=2;
    }
}
