package banking;

/**
 * 메뉴 선택 오류 시 발생하는 개발자 정의 예외
 */
public class MenuSelectException extends Exception {
    public MenuSelectException() {
        super("※ 메뉴 선택 오류: 올바른 메뉴 번호를 입력하세요!");
    }
}
