package oleg.businesslayer;

import oleg.datalayer.MessagesFileManager;
import oleg.models.Message;
import oleg.models.User;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class Messages {
    /**Метод, размещающий сообщение пользователя в БД*/
    public boolean saveNewMessage(Message incomingMessage) {
        MessagesFileManager mfm = MessagesFileManager.getInstance();
        return mfm.createMessage(incomingMessage);
    }

    /**Метод, отправляющий сообщения с сервера получателям*/
    public void sendMessageToTheReciver(Message message) {
        Socket socket = null;
        OutputStream os = null;
        try {
            socket = new Socket(message.getIpReciver(), message.getPortReciver());
            os = socket.getOutputStream();
        } catch (Exception e) {
            return;
        }

        String lineToSent = message.getSender() + ";" + message.getText() + ";";
        try {
            os.write(lineToSent.getBytes(Charset.forName("UTF-8")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        MessagesFileManager mfm = MessagesFileManager.getInstance();
        mfm.settingTheAttributeSent(message);
    }

    /**Метод, возвращающий ArrayList сообщений из БД с признаком "Не отправлено"*/
    public ArrayList<Message> checkNewMessages() {
        MessagesFileManager mfm = MessagesFileManager.getInstance();
        ArrayList <Message> listAllMesages = mfm.getAllMessagesFromDataBase();
        ArrayList<Message> listMessagesToSend = new ArrayList<>();
        if(listAllMesages == null) {
            return listMessagesToSend;
        }
        for(Message messageLineToSend : listAllMesages) {
            if(messageLineToSend.getStatus().equals("Не отправлено")) {
                listMessagesToSend.add(messageLineToSend);
            }
        }
        return listMessagesToSend;
    }

    /**Метод, добавляющий сообщениям к отправке с ip и port получателей сообщений*/
    public void searchIpAndPort(ArrayList<Message> listMessagesToBeSent) {
        MessagesFileManager mfm = MessagesFileManager.getInstance();
        for(Message messageToBeSent : listMessagesToBeSent) {
            String ipAndPort = mfm.getResiverIpAndPort(messageToBeSent);
            String [] ipPort = ipAndPort.split(";");
            messageToBeSent.setIpReciver(ipPort[0]);
            messageToBeSent.setPortReciver(Integer.parseInt(ipPort[1]));
        }
    }
}
