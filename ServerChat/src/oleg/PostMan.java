package oleg;

import oleg.businesslayer.Messages;
import oleg.models.Message;
import oleg.models.User;

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
                for(Message message : listMessagesToBeSent) {
                    messagesManager.sendMessageToTheReciver(message);
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
