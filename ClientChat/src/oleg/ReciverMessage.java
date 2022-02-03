package oleg;

import oleg.businesslayer.Messaging;

public class ReciverMessage implements Runnable {
    @Override
    public void run() {
        Messaging messagingManager = new Messaging();
        while (true) {
            messagingManager.inputMessage();
        }
    }
}
