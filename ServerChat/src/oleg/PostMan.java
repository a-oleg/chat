package oleg;

import oleg.businesslayer.Messages;
import oleg.models.Message;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class PostMan implements Runnable {
    @Override
    public void run() {
        Messages messagesManager = new Messages();
        while (true) {
            ArrayList<Message> listNewMessages = messagesManager.checkNewMessages();
            if(listNewMessages.size() != 0) {
                ArrayList<Message> listMessagesToBeSent = messagesManager.searchIpAndPort(listNewMessages);
                for(Message reciverData : listMessagesToBeSent) {
                    //String [] credentials = reciverData.split(";");
                    // [0] - получатель, [2] - отправитель, [4] - ip получателя, [5] - порт получателя
                    messagesManager.sendMessageToTheReciver(credentials[0], credentials[2], credentials[4], Integer.parseInt(credentials[5]));
                }
            }
            try {
                sleep(10_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
