package oleg;

public class PostMan implements Runnable {
    Thread postThread = new Thread(this, "Поток для почтальона");
    //не понятно, почему не работает метод старт
    postThread.start();

    @Override
    public void run() {

    }
}
