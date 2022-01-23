package oleg.datalayer;

import oleg.models.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class CredentialsFileManager {

    /**Метод, создающий файл-БД с логинами и паролями*/
    private Path getCredentialsFileInstance() throws IOException {
        Path file = Path.of("ClientDataBase.txt");
        if(!Files.exists(Path.of(String.valueOf(file)))) {
            Files.createFile(Path.of("ClientDataBase.txt"));
        }
        return file;
    }

    /**Метод, записывающий в БД логин и пароль*/
    public boolean createNewUser(User user) {
        if(getPasswordByLogin(user.getLogin()) != null) {
            return false;
        }
        try {
            Files.writeString(getCredentialsFileInstance(), user.getLogin() + ";" + user.getPassword() + "\n", StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**Метод, возвращающий пароль из БД*/
    public String getPasswordByLogin(String login) {
        if(login == null) {
            return null;
        }
        Path path = Path.of("ClientDataBase.txt");
        try {
            List<String> arrayCredentials = Files.readAllLines(path);
            for(String s : arrayCredentials) {
                String [] credentials = s.split(";");
                if(login.equals(credentials[0])) {
                    return credentials[1];
                }
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**Метод, добавляющий в БД ip и порт пользователя*/
    public boolean updateAdress (String login, String ip, String port) {
        Path file = Path.of("ClientDataBase.txt");
        try {
            List<String> listCredentials = Files.readAllLines(file);
            for(int i = 0; i < listCredentials.size(); i++) {
                if(listCredentials.get(i).startsWith(login + ";")) {
                    String [] credentials = listCredentials.get(i).split(";");
                    listCredentials.set(i, credentials[0] + ";" + credentials[1] + ";" + ip + ";" + port);
                    Files.write(file,listCredentials);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
