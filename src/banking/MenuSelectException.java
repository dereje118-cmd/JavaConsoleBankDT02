package banking;

public class MenuSelectException extends Exception {
    public MenuSelectException() {
        super("※ 메뉴 선택 오류: 올바른 메뉴 번호를 입력하세요!");
    }
}
