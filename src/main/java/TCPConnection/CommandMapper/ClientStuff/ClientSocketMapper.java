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
import Producer.SocketEnhancedProducer;
import Wrapper.SocketEnhanced;

import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClientSocketMapper implements CommandConsumer, SocketEnhancedProducer {

    private final ObserverObservableMiddleware<SocketEnhanced> observer;
    private final EnhancedSocketActions enhancedSocketActions;
    private final SocketFactory socketFactory;
    private final MessageWrapperParser[] parsers;
    private final MessageConsumer messageConsumer;
    private final SocketEnhancedConsumer socketEnhancedConsumer;
    private final List<SocketReceiverPair> socketReceiverList = Collections.synchronizedList(new ArrayList<>());

    public ClientSocketMapper(SocketEnhancedConsumer socketEnhancedConsumer, MessageConsumer messageConsumer, SocketFactory socketFactory, MessageWrapperParser[] parsers, EnhancedSocketActions enhancedSocketActions, CommandProducer... commandProducers) {
        this.observer = new ObserverObservableMiddleware<>();
        this.enhancedSocketActions = enhancedSocketActions;
        this.socketFactory = socketFactory;
        this.parsers = parsers;
        this.messageConsumer = messageConsumer;
        this.socketEnhancedConsumer = socketEnhancedConsumer;

        for (CommandProducer producer : commandProducers) {
            producer.subscribe(this);
        }
    }

    @Override
    public void consume(Command command) {
        SocketEnhanced socketEnhanced = null;
        SocketCreate socketCreate;
        Socket socket = null;

        switch (command) {
            case CommandOpen open -> {
                socketCreate = socketFactory.make(open);
                try {
                    socket = socketCreate.create();
                    socketEnhanced = new SocketEnhanced(socket,
                            InetAddress.getByName(open.getRemoteIPAddress()),
                            open.getRemotePort(),
                            open.getName());
                    this.subscribe(socketEnhancedConsumer);
                } catch (Exception e) {
                    System.out.println("Could not create an enhanced socket: " + e.getMessage());
                    return;
                }
            }
            case CommandConnect connect -> {
                socketCreate = socketFactory.make(connect);
                try {
                    socket = socketCreate.create();
                    socketEnhanced = new SocketEnhanced(socket,
                            InetAddress.getByName(connect.getRemoteIPAddress()),
                            connect.getRemotePort(),
                            connect.getName());
                    this.subscribe(socketEnhancedConsumer);

                } catch (Exception e) {
                    System.out.println("Could not create an enhanced socket: " + e.getMessage());
                    return;
                }
            }
            case CommandDisconnect ignored -> this.unsubscribe(socketEnhancedConsumer);
            case null, default -> {
                return;
            }
        }

        notifyObserver(socketEnhanced);
        synchronized (socketReceiverList) {
            if (socketEnhanced != null && socket != null) {
                SocketReceiverPair pair = findPairBySocket(socket);
                if (pair == null) {
                    Receive receive = new Receive(socket, parsers);
                    receive.subscribe(messageConsumer);
                    receive.startCommunication();
                    socketReceiverList.add(new SocketReceiverPair(socket, receive));
                    new RemoveClientFromPool(receive, socketEnhanced.getInetAddress(),
                            socketEnhanced.getPort(), enhancedSocketActions,
                            receive);
                }
            }
        }
    }

    private SocketReceiverPair findPairBySocket(Socket socket) {
        synchronized (socketReceiverList) {
            for (SocketReceiverPair pair : socketReceiverList) {
                if (pair.socket().equals(socket)) {
                    return pair;
                }
            }
            return null;
        }
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

    private record SocketReceiverPair(Socket socket, MessageReceiver receiver) {
    }
}

