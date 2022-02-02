package oleg.businesslayer;

import oleg.datalayer.CredentialsFileManager;
import oleg.models.User;

public class Authentication {
    /**Метод, использующийся для занесения логина и пароля пользователя в БД*/
    public boolean registerNewUser(User newUser) {
        CredentialsFileManager cfm = CredentialsFileManager.getInstance();
        return cfm.createNewUser(newUser);
    }

    /**Метод, проверяющий наличие логина и пароля в БД*/
    public boolean authorizeUser(User notAnAuthorizedUser) {
        CredentialsFileManager cfm = CredentialsFileManager.getInstance();
        String passwordByLogin = cfm.getPasswordByLogin(notAnAuthorizedUser);
        if(passwordByLogin == null) {
            return false;
        } else if(passwordByLogin.equals(notAnAuthorizedUser.getPassword())) {
            cfm.updateIpAndPortInDataBase(notAnAuthorizedUser.getLogin(), notAnAuthorizedUser.getIp(), notAnAuthorizedUser.getPort());
            return true;
        } else
            return false;
    }
}
