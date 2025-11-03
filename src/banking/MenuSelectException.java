package banking;

// 메뉴 선택 범위 벗어날 때 발생시키는 사용자 정의 예외
public class MenuSelectException extends Exception {
    public MenuSelectException() {
        super("※ 메뉴 선택 오류: 올바른 메뉴 번호를 입력하세요!");
    }
}
