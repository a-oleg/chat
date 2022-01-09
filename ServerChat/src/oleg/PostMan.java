package oleg;

import oleg.businesslayer.Messages;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class PostMan implements Runnable {

    //Посмотреть паттерн синглтон
    @Override
    public void run() {
        /*
        while (true) {
            Messages m = new Messages();
            m.sendMessageToTheReciver();
            try {
                sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
         */
        Messages m = new Messages();
        while (true) {
            ArrayList<String> al = m.checkNewMessages();
            try {
                sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for(String message : al) {
                System.out.println(message);
            }
        }

    }
}
