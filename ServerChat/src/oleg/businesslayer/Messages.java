package oleg.businesslayer;

import oleg.datalayer.MessagesFileManager;

public class Messages {
    /**Метод, размещающий сообщение пользователя в БД*/
    public boolean saveNewMessage(String receiver, String message, String ip) {
        MessagesFileManager mfm = new MessagesFileManager();
        return mfm.createMessage(receiver, message, ip);
    }

    //Переписать, чтобы клиент сразу присылал свой логин, а этот класс удалить
    /**Метод, проверяющий ip и порт в БД для возврата логина*/
    public String loginByIpAndPort(String ip, String port) {
        MessagesFileManager mfm = new MessagesFileManager();
        mfm.searchLoginByIp(ip);
        //Не дописано
        return "1";
    }
}
