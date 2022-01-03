package oleg.businesslayer;

import oleg.datalayer.CredentialsFileManager;

public class Authentication {
    /**Метод, использующийся для занесения логина и пароля пользователя в БД*/
    public boolean registerNewUser(String login, String password) {
        CredentialsFileManager cfm = new CredentialsFileManager();
        return cfm.createNewUser(login, password);
    }

    /**Метод, проверяющий наличие логина и пароля в БД*/
    public boolean authorizeUser(String login, String password, String ip, String port) {
        CredentialsFileManager cfm = new CredentialsFileManager();
        String passwordByLogin = cfm.getPasswordByLogin(login);
        if(passwordByLogin == null) {
            return false;
        } else if(passwordByLogin.equals(password)) {
            cfm.updateAdress(login, ip, port);
            return true;
        } else
            return false;
    }
}
