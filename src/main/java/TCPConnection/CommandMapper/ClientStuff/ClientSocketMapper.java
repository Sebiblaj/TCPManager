package CommandMapper.ClientStuff;

import Command.*;
import CommandMapper.DisconnectStuff.RemoveClientFromPool;
import CommandMapper.InterfacesFactory.SocketFactory;
import CommandParser.Interfaces.MessageWrapperParser;
import Consumer.CommandConsumer;
import Consumer.*;
import Creator.Interfaces.SocketCreate;
import Executors.Sender.Interfaces.MessageReceiver;
import Executors.Sender.Receive;
import Managers.Actions.EnhancedSocketActions;
import Observer.ObserverObservableMiddleware;
import Producer.CommandProducer;
import Producer.MessageProducer;
import Producer.SocketEnhancedProducer;
import Wrapper.SocketEnhanced;

import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClientSocketMapper implements CommandConsumer, SocketEnhancedProducer {

    private final ObserverObservableMiddleware<SocketEnhanced> observer;
    private final EnhancedSocketActions enhancedSocketActions;
    private final SocketFactory socketFactory;
    private final MessageWrapperParser[] parsers;
    private final Map<Socket, MessageReceiver> receiveMap = new HashMap<>();
    private final MessageConsumer messageConsumer;

    public ClientSocketMapper(MessageConsumer messageConsumer,SocketFactory socketFactory, MessageWrapperParser[] parsers, EnhancedSocketActions enhancedSocketActions, CommandProducer... commandProducers) {
        this.observer = new ObserverObservableMiddleware<>();
        this.enhancedSocketActions = enhancedSocketActions;
        this.socketFactory = socketFactory;
        this.parsers = parsers;
        this.messageConsumer = messageConsumer;

        for (CommandProducer producer : commandProducers) {
            producer.subscribe(this);
        }
    }

    @Override
    public void consume(Command command) {
        SocketEnhanced socketEnhanced;
        SocketCreate socketCreate;
        Socket socket;

        if (command instanceof CommandOpen open) {
            socketCreate = socketFactory.make(open);
            try {
                socket = socketCreate.create();
                socketEnhanced = new SocketEnhanced(socket,
                        InetAddress.getByName(open.getRemoteIPAddress()),
                        open.getRemotePort(),
                        open.getName());
            } catch (Exception e) {
                System.out.println("Could not create an enhanced socket: " + e.getMessage());
                return;
            }
        } else if (command instanceof CommandConnect connect) {
            socketCreate = socketFactory.make(connect);
            try {
                socket = socketCreate.create();
                socketEnhanced = new SocketEnhanced(socket,
                        InetAddress.getByName(connect.getRemoteIPAddress()),
                        connect.getRemotePort(),
                        connect.getName());
            } catch (Exception e) {
                System.out.println("Could not create an enhanced socket: " + e.getMessage());
                return;
            }
        } else {
            return;
        }


        notifyObserver(socketEnhanced);
        MessageReceiver receiver = receiveMap.computeIfAbsent(socket, key -> {
            Receive receive = new Receive(socket, parsers);
            receive.subscribe(messageConsumer);
            receive.startCommunication();
            return receive;
        });

        new RemoveClientFromPool(receiver, socketEnhanced.getInetAddress(),
                socketEnhanced.getPort(), enhancedSocketActions,
                (MessageProducer) receiver);
    }

    @Override
    public void subscribe(Consumer<SocketEnhanced> consumer) {
        observer.subscribe(consumer);
    }

    @Override
    public void unsubscribe(Consumer<SocketEnhanced> consumer) {
        observer.unsubscribe(consumer);
    }

    @Override
    public void notifyObserver(SocketEnhanced socketEnhanced) {
        observer.consume(socketEnhanced);
    }
}
