package Executors.Connectors;

import Command.*;
import Command.DummyCommands.CommandAcceptConnections;
import Command.DummyCommands.CommandTerminate;
import Consumer.*;
import Observer.ObserverObservableMiddleware;
import Producer.CommandProducer;
import Producer.SocketProducer;
import Wrapper.Sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AcceptConnections implements CommandConsumer, SocketProducer {

    private final ObserverObservableMiddleware<Sockets> socketObserver;
    private ServerSocket serverSocket;
    private boolean running;
    private Thread acceptThread;

    public AcceptConnections(CommandProducer... producers) {
        if(producers!=null) {
            for (CommandProducer producer : producers) {
                producer.subscribe(this);
            }
        }
        running = true;
        socketObserver = new ObserverObservableMiddleware<>();
    }

    private void stop() {
        running = false;
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
                System.out.println("ServerSocket closed.");
            } catch (IOException e) {
                System.out.println("Error closing ServerSocket: " + e);
            }
        }
        if (acceptThread != null && !acceptThread.isInterrupted()) {
            acceptThread.interrupt();
        }
    }

    @Override
    public void subscribe(Consumer<Sockets> consumer) {
        socketObserver.subscribe(consumer);
    }

    @Override
    public void unsubscribe(Consumer<Sockets> consumer) {
        socketObserver.unsubscribe(consumer);
    }

    @Override
    public void notifyObserver(Sockets sockets) {
        socketObserver.consume(sockets);
    }

    @Override
    public void consume(Command command) {
        if (command instanceof CommandAcceptConnections commandAcceptConnections) {
            this.serverSocket = commandAcceptConnections.getServerSocket();
            running = true;
            acceptThread = new Thread(() -> {
                try {
                    while (running) {
                        try {
                            Socket socket = serverSocket.accept();
                            System.out.println("Accepted connection from " + socket.getRemoteSocketAddress());
                            Sockets sockets = new Sockets(socket, commandAcceptConnections.getName());
                            notifyObserver(sockets);
                        } catch (IOException e) {
                            if (running) {
                                System.out.println("Error accepting client connection: " + e.getMessage());
                            }
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error accepting client connection: " + e.getMessage());
                }
            });
            acceptThread.start();
        } else if (command instanceof CommandTerminate) {
            stop();
        }
    }
}
