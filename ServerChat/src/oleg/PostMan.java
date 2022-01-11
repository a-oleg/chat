package oleg;

import oleg.businesslayer.Messages;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class PostMan implements Runnable {
    @Override
    public void run() {
        Messages m = new Messages();
        while (true) {
            ArrayList<String> listNewMessage = m.checkNewMessages();
            try {
                sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //SOUP - заглушка, вместо неё отправка на клиент
            for(String message : listNewMessage) {
                System.out.println(message);
            }
        }

    }
}
