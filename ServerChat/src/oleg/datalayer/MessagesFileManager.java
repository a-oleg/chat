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

    /**Метод, записывающий в БД сообщение в формате: отправитель, получатель, текст, признак отправки*/
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
        try {
            fr.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messagesList;
    }

    /**Метод, проставляющий признак "Отправлено" у сообщений в БД*/
    public void settingTheAttributeSent(String sender, String message) {
        ArrayList<String> listMessagesDownloadedFromTheDataBase = getAllMessageFromDataBase();
        ArrayList<String> listMessageToWriteToTheDataBase = new ArrayList<>();
        for (String lineMessage : listMessagesDownloadedFromTheDataBase) {
            if(lineMessage.contains(message) && lineMessage.contains(sender)) {
                String [] lineElement = lineMessage.split(";");
                String newLineMessage = lineElement[0] + ";" + lineElement[1] + ";" + lineElement[2] + ";" + "Отправлено" + ";";
                listMessageToWriteToTheDataBase.add(newLineMessage);
            } else {
                listMessageToWriteToTheDataBase.add(lineMessage);
            }
        }

        Path file = Path.of("MessagesDataBase.txt");
        try {
            Files.delete(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(String line : listMessageToWriteToTheDataBase) {
            String [] lineToWriteToTheDataBase = line.split(";");
            createMessage(lineToWriteToTheDataBase[0], lineToWriteToTheDataBase[1], lineToWriteToTheDataBase[2], lineToWriteToTheDataBase[3]);
        }
    }

    /**Метод, возвращающий IP и порт получателя сообщения по логину из БД*/
    public String getResiverIpAndPort(String resiverLogin) {
        FileReader fr = null;
        try {
            fr = new FileReader("ClientDataBase.txt");
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
                if(lineInDataBase.contains(resiverLogin)) {
                    String [] credentialsLine = lineInDataBase.split(";");
                    ipAndPort = credentialsLine[2] + ";" + credentialsLine[3] + ";";
                    hasMoreLine = false;
                }
            }
        }
        return ipAndPort;
    }
}
