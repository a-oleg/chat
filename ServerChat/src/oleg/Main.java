package oleg;

import oleg.businesslayer.Authentication;
import oleg.businesslayer.Messages;
import oleg.models.Message;
import oleg.models.User;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) {
        PostMan postThread = new PostMan();
        new Thread(postThread).start();

        while (true) {
            try (ServerSocket serverSocket = new ServerSocket(8000);
                 Socket socket = serverSocket.accept();
                 InputStream is = socket.getInputStream();
                 OutputStream os = socket.getOutputStream()) {
                    byte[] bytesClintMessage = new byte[255];
                    is.read(bytesClintMessage);
                    String stringMessage = new String(bytesClintMessage, StandardCharsets.UTF_8);
                    String[] clientMassage = stringMessage.split(";");
                    switch (clientMassage[0]) {
                        case ("register"):
                            User newUser = User.lineToNewObjectUser(stringMessage);
                            Authentication registerManager = new Authentication();
                            if (registerManager.registerNewUser(newUser)) {
                                os.write("200".getBytes(StandardCharsets.UTF_8));
                                os.flush();
                            } else {
                                os.write("400".getBytes(StandardCharsets.UTF_8));
                                os.flush();
                            }
                            break;
                        case ("autorisation"):
                            User notAnAuthorizedUser = User.lineToNewObjectUser(stringMessage);
                            Authentication autorisationManager = new Authentication();
                            if (autorisationManager.authorizeUser(notAnAuthorizedUser)) {
                                os.write("201".getBytes());
                                os.flush();
                            } else {
                                os.write("401".getBytes());
                                os.flush();
                            }
                            break;
                        case("outMessage"):
                            Message incomingMessage = Message.lineToNewObjectMessage(stringMessage);
                            Messages saveMassageManager = new Messages();
                            if(saveMassageManager.saveNewMessage(incomingMessage)) {
                                os.write("202".getBytes());
                                os.flush();
                            } else {
                                os.write("402".getBytes());
                                os.flush();
                            }
                            break;
                        default:
                            os.write("410".getBytes());
                            os.flush();
                            break;
                    }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
