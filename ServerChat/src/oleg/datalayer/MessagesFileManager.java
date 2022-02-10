package oleg.datalayer;

import oleg.models.Message;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class MessagesFileManager {
    //Паттерн Singleton
    private static MessagesFileManager INSTANCE;
    private MessagesFileManager() {}
    public static MessagesFileManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MessagesFileManager();
        }
        return INSTANCE;
    }

    /**Метод, создающий БД-файл с сообщениями*/
    private Path getMessageFileInstance() {
        Path file = Path.of("ServerChat/MessagesDataBase.txt");
        if(!Files.exists(Path.of(String.valueOf(file)))) {
            try {
                Files.createFile(Path.of("ServerChat/MessagesDataBase.txt"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**Метод, записывающий в БД сообщение в формате: отправитель, получатель, текст, признак отправки*/
    public boolean createMessage(Message message) {
        try {
            Files.writeString(getMessageFileInstance(), message.getSender() + ";" + message.getReciver() + ";" + message.getText() + ";" + message.getStatus() + ";" + "\n", StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**Метод, возвращающий ArrayList всех сообщений из БД*/
    public ArrayList<Message> getAllMessagesFromDataBase() {
        FileReader fr;
        try {
            fr = new FileReader("ServerChat/MessagesDataBase.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        BufferedReader br = new BufferedReader(fr);

        ArrayList<Message> messagesList = new ArrayList<>();
        boolean hasMoreLine = true;
        String messageLine;
        while (hasMoreLine) {
            try {
                if (((messageLine = br.readLine()) != null)) {
                    String [] messageData = messageLine.split(";");
                    Message message = new Message(messageData[0], messageData[1], messageData[2], messageData[3]);
                    messagesList.add(message);
                } else {
                    hasMoreLine = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messagesList;
    }

    /**Метод, проставляющий признак "Отправлено" у сообщений в БД*/
    public void settingTheAttributeSent(Message sentMessage) {
        ArrayList<Message> messagesFromDataBase = getAllMessagesFromDataBase();
        ArrayList<Message> listMessagesToWriteToTheDataBase = new ArrayList<>();
        for (Message message : messagesFromDataBase) {
            if(message.equals(sentMessage)) {
                sentMessage.setStatus("Отправлено");
            }
        }

        Path file = Path.of("ServerChat/MessagesDataBase.txt");
        try {
            Files.delete(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(Message message : listMessagesToWriteToTheDataBase) {
            createMessage(message);
        }
    }

    /**Метод, возвращающий IP и порт получателя сообщения*/
    public String getResiverIpAndPort(Message message) {
        FileReader fr = null;
        try {
            fr = new FileReader("ServerChat/ClientDataBase.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fr);

        String ipAndPort = null;
        String lineInDataBase = null;
        boolean hasMoreLine = true;
        while(hasMoreLine){
            try {
                lineInDataBase = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(lineInDataBase == null) {
                hasMoreLine = false;
            } else {
                if(lineInDataBase.contains(message.getReciver() + ";")) {
                    String [] credentialsLine = lineInDataBase.split(";");
                    ipAndPort = credentialsLine[2] + ";" + credentialsLine[3] + ";";
                    hasMoreLine = false;
                }
            }
        }
        try {
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ipAndPort;
    }

}
