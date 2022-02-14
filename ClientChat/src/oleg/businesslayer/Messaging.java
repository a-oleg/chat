package oleg.businesslayer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Messaging {
    /**Метод, отправляющий на сервер исходящее сообщение пользователя*/
    public boolean outputMessage(String nicknameOfReceiver, String textOfMessage) {
        String userOutMessage;
        userOutMessage = "outMessage;" + Authentication.nicknameOfSender + ";" + nicknameOfReceiver + ";" + textOfMessage + ";";
        boolean result = false;
        try(Socket socket = new Socket("0.0.0.0", 8000);
            OutputStream os = socket.getOutputStream()) {
            os.write(userOutMessage.getBytes(Charset.forName("UTF-8")));

            InputStream is = socket.getInputStream();
            byte [] response = new byte[3];
            is.read(response);
            String responseStr = new String(response, StandardCharsets.UTF_8);
            switch (responseStr) {
                case ("202"): System.out.println("Сообщение отправлено");
                    result = true;
                    break;
                case ("402"): System.out.println("Сообщение не отправлено, повторите ввод");
                    break;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**Метод, принимающий с сервера исходящее сообщение иного пользователя*/
    public boolean inputMessage() {
        try(ServerSocket serverSocket = new ServerSocket(8010)) {
            Socket socket = serverSocket.accept();
            InputStream is = socket.getInputStream();
            byte[] bytesServerMessage = new byte[255];
            is.read(bytesServerMessage);
            String stringMessage = new String(bytesServerMessage, StandardCharsets.UTF_8);
            String[] stringServerMassage = stringMessage.split(";");
            Calendar timeRecive = GregorianCalendar.getInstance();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println(dateFormat.format(timeRecive.getTime())+ " Входящее сообщение от " + stringServerMassage[0] + ": " + stringServerMassage[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
