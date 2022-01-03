package oleg.businesslayer;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Messaging {
    /**Метод, отправляющий на сервер исходящее сообщение пользователя*/
    public boolean outMessage(String nicknameOfReceiver, String textOfMessage) {
        int clientPort = 8010;
        String userOutMessage = null;
        try {
            userOutMessage = "outMessage;" + nicknameOfReceiver + ";" + textOfMessage + ";" + InetAddress.getLocalHost().getHostAddress() + ";" + clientPort + ";";
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        boolean result = false;
        try(Socket socket = new Socket("0.0.0.0", 8000);
            OutputStream os = socket.getOutputStream()) {
            os.write(userOutMessage.getBytes(Charset.forName("UTF-8")));

            InputStream is = socket.getInputStream();
            byte [] response = new byte[3];
            is.read(response);
            String responseStr = new String(response, StandardCharsets.UTF_8);
            switch (responseStr) {
                case ("202"): System.out.println("Сообщение доставлено");
                    result = true;
                    break;
                case ("402"): System.out.println("Сообщение не доставлено");
                    break;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
