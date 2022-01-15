package oleg.businesslayer;

import oleg.datalayer.MessagesFileManager;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class Messages {
    /**Метод, размещающий сообщение пользователя в БД*/
    public boolean saveNewMessage(String sender, String receiver, String message) {
        MessagesFileManager mfm = new MessagesFileManager();
        return mfm.createMessage(sender, receiver, message, "Не отправлено");
    }

    //Не дописал, не очень понял, что там с портами
    /**Метод, отправляющий сообщения с сервера получателям*/
    public void sendMessageToTheReciver() {
        Socket socket = null;
        try {
            socket = new Socket("0.0.0.0", 8000);
        } catch (IOException e) {
            e.printStackTrace();
        }

        OutputStream os = null;
        try {
            os = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<String> messagesToSend = checkNewMessages();
        for(String inputMassage : messagesToSend) {
            String[] lineMessage = inputMassage.split(";");
            //Формат lineToSent: 0 - отправитель, 2 - текст
            String lineToSent = lineMessage[0] + ";" + lineMessage[2] + ";";
            try {
                os.write(lineToSent.getBytes(Charset.forName("UTF-8")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            MessagesFileManager mfm = new MessagesFileManager();
            mfm.settingTheAttributeSent(inputMassage);
        }
    }

    /**Метод, возвращающий ArrayList сообщений из БД с признаком "Не отправлено"*/
    public ArrayList<String> checkNewMessages() {
        MessagesFileManager mfm = new MessagesFileManager();
        ArrayList <String> listAllMesages = mfm.getAllMessageFromDataBase();
        ArrayList<String> listMessagesToSend = new ArrayList<String>();
        for(String messageLineToSend : listAllMesages) {
            if(messageLineToSend.contains("Не отправлено")) {
                listMessagesToSend.add(messageLineToSend);
            }
        }
        return listMessagesToSend;
    }

    /**Метод, возвращающий ArrayList сообщений к отправке с ip и port получателей сообщений*/
    public ArrayList<String> searchIpAndPort(ArrayList<String> listMessagesToBeSent) {
        MessagesFileManager mfm = new MessagesFileManager();
        ArrayList<String> messagesWithTheIpAndPort = new ArrayList<>();
        for(String messageToBeSent : listMessagesToBeSent) {
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
