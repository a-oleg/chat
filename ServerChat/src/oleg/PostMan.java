package oleg;

import oleg.businesslayer.Messages;

import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Thread.sleep;

public class PostMan implements Runnable {
    @Override
    public void run() {
        Messages messagesManager = new Messages();
        while (true) {
            //Проверяется наличие новых сообщений и получается их список
            ArrayList<String> listNewMessage = messagesManager.checkNewMessages();

            //Создаём ArrayList и заносим туда отправителя, получателя, текст
            if(listNewMessage.size() != 0) {
                ArrayList <String> listMessagesToBeSent = new ArrayList();
                for(String lineMessage : listNewMessage) {
                    String [] dataMessage = lineMessage.split(";");
                    //элемент 0 - отправитель, элемент 1 - получатель, элемент 2 - текст сообщения
                    listMessagesToBeSent.add(dataMessage[0] + ";" + dataMessage[1] + ";" + dataMessage[2] + ";" + messagesManager.searchIpAndPortByLogin(dataMessage[1]));
                }
            }
            //По логину получателя получаем ip и порт получателя и добавляем его в HashMap


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
