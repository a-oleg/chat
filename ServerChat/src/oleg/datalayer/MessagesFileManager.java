package oleg.datalayer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class MessagesFileManager {
    /**Метод, создающий БД-файл с сообщениями*/
    private Path getMessageFileInstance() throws IOException {
        Path file = Path.of("MessagesDataBase.txt");
        if(!Files.exists(Path.of(String.valueOf(file)))) {
            Files.createFile(Path.of("MessagesDataBase.txt"));
        }
        return file;
    }

    /**Метод, записывающий в БД сообщение: отправитель, получатель, текст*/
    public boolean createMessage(String receiver, String message, String ip) {
        if(searchLoginByIp(ip) == null) return false;
        try {
            Files.writeString(getMessageFileInstance(), searchLoginByIp(ip) + ";" + receiver + ";" + message + ";" + "\n", StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //Перенести в креденшелсфайлменеджер
    /**Метод, возвращающий логин отправителя по ip из БД*/
    public static String searchLoginByIp(String ip) {
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
