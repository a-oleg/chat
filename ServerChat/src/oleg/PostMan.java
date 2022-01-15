package oleg;

import oleg.businesslayer.Messages;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class PostMan implements Runnable {
    @Override
    public void run() {
        Messages messagesManager = new Messages();
        //Проверяется наличие новых сообщений и получается их список
        ArrayList<String> listNewMessage = messagesManager.checkNewMessages();
        //По логину получателя получаем ip и порт получателя
        if(listNewMessage.size() != 0) {
            ArrayList<String> listMessagesToBeSent = messagesManager.searchIpAndPort(listNewMessage);
        }




            /*
            if(listNewMessage.size() != 0) {
                ArrayList <String> listMessagesToBeSent = new ArrayList();
                for(String lineMessage : listNewMessage) {
                    String [] dataMessage = lineMessage.split(";");
                    //dataMessage[0] - отправитель, dataMessage[1] - получатель, dataMessage[2] - текст сообщения
                    listMessagesToBeSent.add(dataMessage[0] + ";" + dataMessage[1] + ";" + dataMessage[2] + ";" + messagesManager.searchIpAndPortByLogin(dataMessage[1]));
                }
            }
            */


            //Сообщения отправляются, используя ip и порт получателя в формате: отправитель сообщения, текст. Из HashMap извлекаются получатель, сообщение, ip-получателя и его порт, которые отправляются в sendMessageToTheReciver

            //Программа засыпает на 10 сек
            try {
                sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

    }
}
