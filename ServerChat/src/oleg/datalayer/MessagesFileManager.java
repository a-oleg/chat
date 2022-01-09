package oleg.datalayer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class MessagesFileManager {
    /**Метод, создающий БД-файл с сообщениями*/
    private Path getMessageFileInstance() {
        Path file = Path.of("MessagesDataBase.txt");
        if(!Files.exists(Path.of(String.valueOf(file)))) {
            try {
                Files.createFile(Path.of("MessagesDataBase.txt"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**Метод, записывающий в БД сообщение: отправитель, получатель, текст, признак отправки*/
    public boolean createMessage(String sender, String receiver, String message, String attribute) {
        try {
            Files.writeString(getMessageFileInstance(), sender + ";" + receiver + ";" + message + ";" + attribute + ";" + "\n", StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**Метод, возвращающий List всех сообщений из БД*/
    public ArrayList getAllMessageFromDataBase() {
        FileReader fr = null;
        try {
            fr = new FileReader("MessagesDataBase.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fr);

        ArrayList<String> messagesList = new ArrayList<>();
        boolean hasMoreLine = true;
        String messageLine;
        while (hasMoreLine) {
            try {
                if (((messageLine = br.readLine()) != null)) {
                    messagesList.add(messageLine);
                } else {
                    hasMoreLine = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return messagesList;

    }

    //Получается, сообщения в БД перемешиваются
    /**Метод, проставляющий признак "Отправлено" у сообщений в БД*/
    public boolean settingTheAttributeSent(String lineForTheAttributeSent) {
        ArrayList<String> allLineMessages = getAllMessageFromDataBase();
        if(allLineMessages.contains(lineForTheAttributeSent)) {
            allLineMessages.remove(lineForTheAttributeSent);

            String[] newLineForDB = lineForTheAttributeSent.split(";");
            createMessage(newLineForDB[0], newLineForDB[1], newLineForDB[2], "Отправлено");
            return true;
        } else {
            return false;
        }
    }

    //Переписать, нужен IP и порт по логину + и использовать при отправке сообщения
    /**Метод, возвращающий логин отправителя по ip из БД*/
    public String searchLoginByIp(String ip) {
        FileReader fr = null;
        try {
            fr = new FileReader("ClientDataBase.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fr);

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

        String login = null;
        for(String lineInTheDataBase : credentialsList) {
            if(lineInTheDataBase.contains(ip)) {
                String [] substringLine = lineInTheDataBase.split(";");
                login = substringLine[0];
            }
        }
        return login;
    }

}
