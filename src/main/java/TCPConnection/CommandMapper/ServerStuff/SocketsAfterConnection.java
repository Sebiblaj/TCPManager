package CommandMapper.ServerStuff;

import CommandMapper.DisconnectStuff.RemoveClientFromServerPool;
import CommandParser.Interfaces.MessageWrapperParser;
import Consumer.MessageConsumer;
import Consumer.SocketConsumer;
import Executors.Sender.Interfaces.MessageReceiver;
import Executors.Sender.Receive;
import Managers.Actions.ServerSocketActions;
import Producer.MessageProducer;
import Producer.SocketProducer;
import Wrapper.Sockets;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SocketsAfterConnection implements SocketConsumer {

    private final ServerSocketActions serverSocketActions;
    private final ServerSocket serverSocket;
    private final Map<Socket, MessageReceiver> receiveMap = new HashMap<>();
    private final MessageWrapperParser[] messageWrapperParsers;
    private final MessageConsumer messageConsumer;

    public SocketsAfterConnection(MessageConsumer messageConsumer,ServerSocket serverSocket, ServerSocketActions serverSocketActions, MessageWrapperParser[] messageWrapperParsers, SocketProducer... socketProducers) {
        this.serverSocketActions = serverSocketActions;
        this.serverSocket = serverSocket;
        this.messageWrapperParsers = messageWrapperParsers;
        this.messageConsumer = messageConsumer;

        for (SocketProducer socketProducer : socketProducers) {
            socketProducer.subscribe(this);
        }
    }

    @Override
    public void consume(Sockets socket) {
        try {
            Socket clientSocket = socket.socket();
            serverSocketActions.addClient(serverSocket.getInetAddress(), serverSocket.getLocalPort(), clientSocket, socket.name());

            MessageReceiver receive = receiveMap.computeIfAbsent(clientSocket, key -> {
                Receive receiver = new Receive(clientSocket, messageWrapperParsers);
                receiver.subscribe(messageConsumer);
                receiver.startCommunication();
                return receiver;
            });

            new RemoveClientFromServerPool(receive, serverSocketActions, (MessageProducer) receive);
        } catch (Exception e) {
            System.out.println("Error handling socket connection: " + e.getMessage());
        }
    }
}
