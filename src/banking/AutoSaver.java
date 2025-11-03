package banking;

import java.io.*;
import java.util.Set;

/**
 * AutoSaver
 * - HashSet<Account> 자동 저장 스레드
 * - 5초마다 AutoSaveAccount.txt로 저장
 * - 데몬 스레드로 종료 시 자동 종료
 */
public class AutoSaver extends Thread {
    private Set<Account> accSet;
    private volatile boolean running = true;

    public AutoSaver(Set<Account> accSet) {
        this.accSet = accSet;
        setDaemon(true);
    }

    @Override
    public void run() {
        while(running){
            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("AutoSaveAccount.txt"));
                oos.writeObject(accSet);
                oos.close();
                Thread.sleep(5000);
            } catch(Exception e){ e.printStackTrace(); }
        }
    }

    public void stopAutoSave() { running = false; }
}
