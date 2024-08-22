package Executors.Sender;

import CommandParser.Interfaces.MessageWrapperParser;
import Consumer.Consumer;
import Executors.Sender.Interfaces.MessageReceiver;
import Message.*;
import Observer.ObserverObservableMiddleware;
import Producer.MessageProducer;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Receive implements Runnable, MessageReceiver, MessageProducer {

    private final Socket socket;
    private final ObserverObservableMiddleware<Message> observer;
    private volatile boolean running;
    private final MessageWrapperParser[] messageWrapperParsers;

    public Receive(Socket socket, MessageWrapperParser[] messageWrapperParsers) {
        this.socket = socket;
        this.observer = new ObserverObservableMiddleware<>();
        this.running = true;
        this.messageWrapperParsers = messageWrapperParsers;
    }

    @Override
    public void startCommunication() {
        Thread readThread = new Thread(this);
        readThread.start();
    }

    private void readMessages() throws IOException {
        InputStream inputStream = socket.getInputStream();
        byte[] buffer = new byte[4096];

        int bytesRead;
        StringBuilder receivedBuilder = new StringBuilder();

        while (running && (bytesRead = inputStream.read(buffer)) != -1) {
            receivedBuilder.append(new String(buffer, 0, bytesRead, StandardCharsets.UTF_8));

            String received = receivedBuilder.toString();
            MessageWrapper messageWrapper = null;
            Message message = null;

            for (MessageWrapperParser parser : messageWrapperParsers) {
                messageWrapper = parser.parse(received);
                if (messageWrapper != null && messageWrapper.lastIndex() >= 0) {
                    receivedBuilder.delete(0, messageWrapper.lastIndex());
                    message=messageWrapper.message();
                    break;
                }
            }
            if (message!=null && received.contains("The server has disconnected")) {
                notifyObserver(new Message("Server disconnected"));
                System.out.println(received);
                break;
            }

            if (message!=null && received.contains("A client has disconnected")) {
                notifyObserver(new SocketMessage("Client disconnected",socket));
                System.out.println(received);
                break;
            }

            System.out.println("Received: " + received);

            if (received.trim().equals("close")) {
                socket.close();
                break;
            }

            if (messageWrapper != null && messageWrapper.message().getMessage() != null) {
                notifyObserver(message);
            }
        }
    }

    @Override
    public void run() {
        try {
            readMessages();
        } catch (IOException e) {
            System.out.println("Connection has been closed");
        } finally {
            stop();
        }
    }

    @Override
    public synchronized void subscribe(Consumer<Message> consumer) {
        observer.subscribe(consumer);
    }

    @Override
    public synchronized void unsubscribe(Consumer<Message> consumer) {
        observer.unsubscribe(consumer);
    }

    @Override
    public synchronized void notifyObserver(Message message) {
        observer.consume(message);
    }

    @Override
    public void stop() {
        running = false;
        try {
            if (!socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            System.out.println("Error closing socket: " + e);
        }
    }
}
