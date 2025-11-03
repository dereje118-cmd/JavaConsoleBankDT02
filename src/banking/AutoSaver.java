package banking;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Set;

// 계좌 정보를 5초마다 자동 저장하는 쓰레드
public class AutoSaver extends Thread {
    private Set<Account> accSet;   // 저장 대상 계좌
    private volatile boolean running = true; // 쓰레드 실행 여부

    public AutoSaver(Set<Account> accSet) {
        this.accSet = accSet;
        setDaemon(true); // 데몬 쓰레드: 프로그램 종료시 같이 종료
    }

    @Override
    public void run() {
        while (running) {
            try {
                FileOutputStream fos = new FileOutputStream("AutoSaveAccount.txt");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(accSet); // 계좌 정보 직렬화
                oos.close();

                Thread.sleep(5000); // 5초마다 저장
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 자동저장 중지
    public void stopAutoSave() {
        running = false;
    }
}
