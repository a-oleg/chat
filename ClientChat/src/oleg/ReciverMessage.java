package oleg;

import oleg.businesslayer.Messaging;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ReciverMessage implements Runnable {
    @Override
    public void run() {
        Messaging messagingManager = new Messaging();
        while (true) {
            messagingManager.inputMessage();
        }
    }
}