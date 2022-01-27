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
        MessagesFileManager mfm = new MessagesFileManager();
        return mfm.createMessage(incomingMessage);
    }

    /**Метод, отправляющий сообщения с сервера получателям*/
    public void sendMessageToTheReciver(Message message) {
        Socket socket = null;
        OutputStream os = null;
        try {
            socket = new Socket(reciver.getIp(), reciver.getPort());
            os = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        String lineToSent = message.getSender() + ";" + message.getText() + ";";
        try {
            os.write(lineToSent.getBytes(Charset.forName("UTF-8")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        MessagesFileManager mfm = new MessagesFileManager();
        mfm.settingTheAttributeSent(message);
    }

    /**Метод, возвращающий ArrayList сообщений из БД с признаком "Не отправлено"*/
    public ArrayList<Message> checkNewMessages() {
        MessagesFileManager mfm = new MessagesFileManager();
        ArrayList <Message> listAllMesages = mfm.getAllMessagesFromDataBase();
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
        ArrayList<Message> messagesWithTheIpAndPort = new ArrayList<>();
        for(Message messageToBeSent : listMessagesToBeSent) {

        }
        //ArrayList<Message> messagesWithTheIpAndPort = new ArrayList<>();
        //for(Message messageToBeSent : listMessagesToBeSent) {
            //String [] lineMessageToBeSent = messageToBeSent.split(";");
            //[1] - логин получателя
            String ipAndPort = mfm.getResiverIpAndPort(messageToBeSent.getReciver());
            //String [] arrayResiverIpAndPort = ipAndPort.split(";");
            //[0] - логин отправителя, [1] - логин получателя, [2] - текст сообщения, [3] - статус, [4] - ip получателя, [5] - порт получателя
            //messagesWithTheIpAndPort.add(lineMessageToBeSent[0] + ";" + lineMessageToBeSent[1] + ";" + lineMessageToBeSent[2] + ";" + lineMessageToBeSent[3] + ";" + arrayResiverIpAndPort[0] + ";" + arrayResiverIpAndPort[1] + ";");
            //}
        return messagesWithTheIpAndPort;
    }
}
