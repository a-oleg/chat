package oleg.businesslayer;

import oleg.datalayer.MessagesFileManager;

import java.io.IOException;
import java.io.OutputStream;
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
    public boolean sendMessageToTheReciver() {
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

        ArrayList<String> messageToSend = chackNewMessage();
        for(String inputMassage : messageToSend) {
            String[] lineMessage = inputMassage.split(";");
            String lineToSent = lineMessage[0] + ";" + lineMessage[1] + ";" + lineMessage[2] + ";";
            try {
                os.write(lineToSent.getBytes(Charset.forName("UTF-8")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            MessagesFileManager mfm = new MessagesFileManager();
            mfm.settingTheAttributeSent(inputMassage);
        }
        return true;
    }

    //Нужно переделать к отправке, используя метод sendMessageToTheReciver
    /**Метод, возвращающий List сообщений из БД с признаком "Не отправлено"*/
    public ArrayList chackNewMessage() {
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
}
