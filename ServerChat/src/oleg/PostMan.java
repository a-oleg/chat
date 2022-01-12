package oleg;

import oleg.businesslayer.Messages;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class PostMan implements Runnable {
    @Override
    public void run() {
        Messages m = new Messages();
        while (true) {
            //Проверяется наличие новых сообщений и получается их список
            ArrayList<String> listNewMessage = m.checkNewMessages();

            //Создаём HashMap и заносим туда отправителя, получателя, текст

            //По логину получателя получаем ip и порт получателя и добавляем его в HashMap
            if(listNewMessage.size() != 0) {

            }


            //Сообщения отправляются, используя ip и порт получателя в формате: отправитель сообщения, текст. Из HashMap извлекаются получатель, сообщение, ip-получателя и его порт, которые отправляются в sendMessageToTheReciver
            for(String message : listNewMessage) {
                System.out.println(message);
            }

            //Программа засыпает на 10 сек
            try {
                sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
