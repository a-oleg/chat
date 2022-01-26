package oleg.businesslayer;

import oleg.datalayer.MessagesFileManager;
import oleg.models.Message;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class Messages {
    /**Метод, размещающий сообщение пользователя в БД*/
    public boolean saveNewMessage(Message incomingMessage) {
        MessagesFileManager mfm = new MessagesFileManager();
        return mfm.createMessage(incomingMessage.getSender(), incomingMessage.getReciver(), incomingMessage.getText(), incomingMessage.getStatus());
    }

    /**Метод, отправляющий сообщения с сервера получателям*/
    public void sendMessageToTheReciver(String sender, String message, String ipReciver, int portReciver) {
        Socket socket = null;
        OutputStream os = null;
        try {
            socket = new Socket(ipReciver, portReciver);
            os = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        String lineToSent = sender + ";" + message + ";";
        try {
            os.write(lineToSent.getBytes(Charset.forName("UTF-8")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        MessagesFileManager mfm = new MessagesFileManager();
        mfm.settingTheAttributeSent(sender, message);
    }

    /**Метод, возвращающий ArrayList сообщений из БД с признаком "Не отправлено"*/
    public ArrayList<Message> checkNewMessages() {
        MessagesFileManager mfm = new MessagesFileManager();
        ArrayList <Message> listAllMesages = mfm.getAllMessageFromDataBase();
        ArrayList<Message> listMessagesToSend = new ArrayList<>();
        for(Message messageLineToSend : listAllMesages) {
            if(messageLineToSend.getStatus().equals("Не отправлено")) {
                listMessagesToSend.add(messageLineToSend);
            }
        }
        return listMessagesToSend;
    }

    /**Метод, возвращающий ArrayList сообщений к отправке с ip и port получателей сообщений*/
    public ArrayList<Message> searchIpAndPort(ArrayList<Message> listMessagesToBeSent) {
        MessagesFileManager mfm = new MessagesFileManager();
        ArrayList<String> messagesWithTheIpAndPort = new ArrayList<>();
        for(Message messageToBeSent : listMessagesToBeSent) {
            String [] lineMessageToBeSent = messageToBeSent.split(";");
            //[1] - логин получателя
            String ipAndPort = mfm.getResiverIpAndPort(lineMessageToBeSent[1]);
            String [] arrayResiverIpAndPort = ipAndPort.split(";");
            //[0] - логин отправителя, [1] - логин получателя, [2] - текст сообщения, [3] - статус, [4] - ip получателя, [5] - порт получателя
            messagesWithTheIpAndPort.add(lineMessageToBeSent[0] + ";" + lineMessageToBeSent[1] + ";" + lineMessageToBeSent[2] + ";" + lineMessageToBeSent[3] + ";" + arrayResiverIpAndPort[0] + ";" + arrayResiverIpAndPort[1] + ";");
        }
        return messagesWithTheIpAndPort;
    }
}
