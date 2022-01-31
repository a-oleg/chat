package oleg.businesslayer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Authentication {
    public static String nicknameOfSender = null;

    /**Метод, направляющий логин и пароль на сервер для регистрации*/
    public boolean registerNewUser(String userLogin, String userPassword) {
        int clientPort = 8010;
        String userLoginAndPassword = null;
        try {
            userLoginAndPassword = "register;" + userLogin + ";" + userPassword + ";" + InetAddress.getLocalHost().getHostAddress() + ";" + clientPort + ";";
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        boolean result = false;
        try (Socket socket = new Socket("0.0.0.0", 8000);
             OutputStream os = socket.getOutputStream();
             InputStream is = socket.getInputStream()) {
                os.write(userLoginAndPassword.getBytes(Charset.forName("UTF-8")));

                byte [] response = new byte[3];
                is.read(response);
                String responseStr = new String(response, StandardCharsets.UTF_8);
                switch (responseStr) {
                    case ("200"): System.out.println("Регистрация завершена успешно");
                        result = true;
                        break;
                    case ("400"): System.out.println("Регистрация не завершена. Произошла ошибка");
                        break;
                    case ("410"): System.out.println("Возникла неизвестная ошибка, операция не выполнена");
                        break;
                    default: System.out.println("Возникла неизвестная ошибка,операция не выполнена");
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        return result;
    }

    /**Метод, направляющий логин и пароль на сервер для авторизации*/
    public boolean authorizeUser (String userLogin, String userPassword) {
        int clientPort = 8010;
        String userLoginAndPassword = null;
        try {
            userLoginAndPassword = "autorisation;" + userLogin + ";" + userPassword + ";" + InetAddress.getLocalHost().getHostAddress() + ";" + clientPort + ";";
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        boolean result = false;
        try(Socket socket = new Socket("0.0.0.0", 8000);
            OutputStream os = socket.getOutputStream()) {
            os.write(userLoginAndPassword.getBytes(Charset.forName("UTF-8")));

            InputStream is = socket.getInputStream();
            byte [] response = new byte[3];
            is.read(response);
            String responseStr = new String(response, StandardCharsets.UTF_8);
            switch (responseStr) {
                case ("201"): System.out.println("Авторизация завершена успешно");
                    nicknameOfSender = userLogin;
                    result = true;
                    break;
                case ("401"): System.out.println("Авторизация не завершена. Произошла ошибка");
                    break;
                case ("402"): System.out.println("Возникла неизвестная ошибка, операция не выполнена");
                    break;
                default: System.out.println("Возникла неизвестная ошибка,операция не выполнена");
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
