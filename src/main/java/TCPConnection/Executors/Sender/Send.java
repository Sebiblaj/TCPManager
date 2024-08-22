package Executors.Sender;

import Executors.Sender.Interfaces.MessageSender;
import Message.Message;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Send implements MessageSender {

    private final Socket socket;

    public Send(Socket socket) {
        this.socket = socket;
    }

    public synchronized void sendMessage(Message message) {
        String messageString = message.getMessage();
        if (socket != null && !socket.isClosed()) {
            try {
                OutputStream outputStream = socket.getOutputStream();
                outputStream.write(messageString.getBytes());
                outputStream.flush();
            } catch (IOException e) {
                try {
                    socket.close();
                } catch (IOException closeException) {
                    System.out.println("Failed to close socket: " + closeException.getMessage());
                }

            }
        } else {
            System.out.println("No connection established.");
        }
    }
}

