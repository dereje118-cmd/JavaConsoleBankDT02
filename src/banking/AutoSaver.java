package banking;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Set;

public class AutoSaver extends Thread {
    private Set<Account> accSet;
    private volatile boolean running = true;

    public AutoSaver(Set<Account> accSet) {
        this.accSet = accSet;
        setDaemon(true);
    }

    @Override
    public void run() {
        while (running) {
            try {
                FileOutputStream fos = new FileOutputStream("AutoSaveAccount.txt");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(accSet);
                oos.close();
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void stopAutoSave() {
        running = false;
    }
}
