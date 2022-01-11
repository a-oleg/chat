package oleg.businesslayer;

import oleg.datalayer.MessagesFileManager;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

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

    //Нужно переделать к отправке, используя метод sendMessageToTheReciver
    /**Метод, возвращающий List сообщений из БД с признаком "Не отправлено"*/
    public ArrayList checkNewMessages() {
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

    /**Метод, возвращающий ip и порт отправителя по логину*/
    public HashMap searchIpAndPortByLogin(String login) {
        MessagesFileManager mfm = new MessagesFileManager();
        HashMap<String, Integer> userIpAndPorts = mfm.getUserIpAndPorts();
        //

        ArrayList<String> credentialsList = new ArrayList<>();
        String credentialsLine = null;
        while (true) {
            try {
                if (!((credentialsLine = br.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            credentialsList.add(credentialsLine);
        }

        String ip = null;
        for(String lineInTheDataBase : credentialsList) {
            if(lineInTheDataBase.contains(login)) {
                String [] substringLine = lineInTheDataBase.split(";");
                ip = substringLine[2];
            }
        }
    }
}
