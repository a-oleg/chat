package oleg;

public class PostMan implements Runnable {
    Thread postThread = new Thread(this, "Поток для почтальона");
    postThread.start();

    @Override
    public void run() {

    }
}
