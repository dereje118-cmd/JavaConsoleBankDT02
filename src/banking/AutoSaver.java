package banking;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Set;

// AutoSaver: 계좌 정보를 주기적으로 자동 저장하는 쓰레드 클래스
public class AutoSaver extends Thread {

    // 자동 저장 대상이 되는 계좌 Set
    private Set<Account> accSet;

    // 쓰레드 실행 상태를 제어하는 플래그
    // volatile: 멀티 쓰레드 환경에서 값이 항상 최신 상태로 읽히도록 함
    private volatile boolean running = true;

    // 생성자: 저장할 계좌 Set을 전달받음
    public AutoSaver(Set<Account> accSet) {
        this.accSet = accSet;
        setDaemon(true); // 데몬 쓰레드로 설정 -> 메인 프로그램 종료 시 자동 종료
    }

    // 쓰레드가 실행될 때 동작하는 메서드
    @Override
    public void run() {
        while (running) { // running이 true인 동안 반복
            try {
                // 계좌 정보를 파일로 저장
                FileOutputStream fos = new FileOutputStream("AutoSaveAccount.txt");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(accSet); // Set 전체 직렬화하여 파일 저장
                oos.close();

                // 5초 대기 후 다음 저장
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace(); // 예외 발생 시 출력
            }
        }
    }

    // 외부에서 쓰레드를 종료시키는 메서드
    public void stopAutoSave() {
        running = false; // while 루프 종료 -> 쓰레드 종료
    }
}
