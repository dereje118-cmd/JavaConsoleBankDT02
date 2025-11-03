package banking;

// MenuSelectException: 메뉴 선택 시 잘못된 입력을 처리하기 위한 사용자 정의 예외 클래스
// Exception을 상속받아 개발자가 직접 정의한 메시지 출력 가능
public class MenuSelectException extends Exception {

    // 생성자: 예외 발생 시 출력할 메시지 지정
    public MenuSelectException() {
        // 부모 Exception 클래스 생성자에 메시지 전달
        super("※ 메뉴 선택 오류: 올바른 메뉴 번호를 입력하세요!");
    }
}
