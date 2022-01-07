package oleg.uilayer;

import oleg.businesslayer.Authentication;
import oleg.businesslayer.Messaging;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleInterface {
    private static boolean isExit = false;

    /**Метод, реализующий диалог с клиентом: хочет он зарагестрироваться или авторизоваться*/
    public void entryDialog() {
        boolean invalidResult = true;
        do {
            if(ConsoleInterface.isExit == true) {
                break;
            }

            System.out.println("Для завершения работы введите \"Exit\" и нажмите Enter");
            System.out.println("Для регистрации нажмите \"R\", а для авторизации нажмите \"A\" и подтвердите ввод нажатием клавиши \"Enter\"");
            BufferedReader userChoice = new BufferedReader(new InputStreamReader(System.in));
            String userChoiceStr = null;
            try {
                userChoiceStr = userChoice.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            switch (userChoiceStr) {
                case ("r"):
                case ("R"):
                    registerDialog();
                    invalidResult = false;
                    break;
                case ("a"):
                case ("A"):
                    while (!autorisationDialog()) {
                        autorisationDialog();
                    }
                    while (!isExit) {
                            messagingDialog();
                        }
                    invalidResult = false;
                    break;
                case ("exit"):
                case ("Exit"): isExit(userChoiceStr);
                    invalidResult = false;
                break;
                default: System.out.println("Неверный выбор. Нажмите \"R\" или \"A\" и подтвердите нажатием клавиши \"Enter\"");
                    break;
            }
        }
        while (invalidResult && !isExit);
        System.out.println("Программа завершена");
    }

    /**Метод, завершающий работу с программой при вводе в консоль слова "Exit"*/
    private boolean isExit(String userMessage) {
        if(userMessage.equals("Exit") | userMessage.equals("exit")) {
            return true;
        } return false;
    }

    /**Метод, запрашивающий у клиента логин и пароль, в случае регистрации*/
    boolean registerDialog() {
        BufferedReader registerLoginAndPassword = new BufferedReader(new InputStreamReader(System.in));
        String login = null;
        String password = null;
        try {
            System.out.println("Для регистрации введите логин и нажмите \"Enter\"");
            login = registerLoginAndPassword.readLine();
            if(isExit(login)) {
                isExit = true;
                return false;
            }
            System.out.println("Для регистрации введите пароль и нажмите \"Enter\"");
            password = registerLoginAndPassword.readLine();
            if(isExit(password)) {
                isExit = true;
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Authentication registerManager = new Authentication();
        return registerManager.registerNewUser(login, password);
    }

    /**Метод, запрашивающий у клиента логин и пароль, в случае авторизации*/
    boolean autorisationDialog() {
        BufferedReader autorisationLoginAndPassword = new BufferedReader(new InputStreamReader(System.in));
        String login = null;
        String password = null;
        System.out.println("Для авторизации, введите логин и нажмите \"Enter\"");
        try {
        login = autorisationLoginAndPassword.readLine();
            isExit(login);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Для авторизации, введите пароль и нажмите \"Enter\"");
        try {
            password = autorisationLoginAndPassword.readLine();
            isExit(password);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Authentication authorizeManager = new Authentication();
        return authorizeManager.authorizeUser(login, password);
    }

    /**Метод, отправляющий сообщение пользователя на сервер*/
    boolean messagingDialog() {
        BufferedReader autorisationLoginAndPassword = new BufferedReader(new InputStreamReader(System.in));
        String nicknameOfReceiver = null;
        String textOfMessage = null;
        System.out.println("Введите никнейм получателя сообщения и нажмите \"Enter\"");
        try {
            nicknameOfReceiver = autorisationLoginAndPassword.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        isExit(nicknameOfReceiver);
        System.out.println("Введите текст сообщения и нажмите \"Enter\"");
        try {
            textOfMessage = autorisationLoginAndPassword.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        isExit(textOfMessage);
        Messaging messageManager = new Messaging();
        return messageManager.outMessage(nicknameOfReceiver, textOfMessage);
    }
}
