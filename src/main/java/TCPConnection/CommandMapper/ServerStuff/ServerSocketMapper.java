package CommandMapper.ServerStuff;

import Command.Command;
import Command.CommandListen;
import CommandMapper.InterfacesFactory.ServerSocketFactory;
import Consumer.CommandConsumer;
import Consumer.Consumer;
import Creator.Interfaces.ServerSocketCreate;
import Observer.ObserverObservableMiddleware;
import Producer.CommandProducer;
import Producer.ServerSocketProducer;
import Wrapper.ServerSockets;


public class ServerSocketMapper implements CommandConsumer, ServerSocketProducer {

    private final ObserverObservableMiddleware<ServerSockets> observer;
    private final ServerSocketFactory serverSocketFactory;

    public ServerSocketMapper(ServerSocketFactory serverSocketFactory,CommandProducer... commandProducers) {
        this.observer = new ObserverObservableMiddleware<>();
        for (CommandProducer producer : commandProducers) {
            producer.subscribe(this);
        }
        this.serverSocketFactory = serverSocketFactory;
    }

    @Override
    public void consume(Command command) {
        ServerSockets serverSocket;
        if (command instanceof CommandListen listenCommand) {
             ServerSocketCreate serverSocketCreate=serverSocketFactory.make(listenCommand);
             serverSocket = serverSocketCreate.create();
            if (serverSocket != null) {
                notifyObserver(serverSocket);
            }
        }

    }

    public void subscribe(Consumer<ServerSockets> consumer) {
        observer.subscribe(consumer);
    }

    public void unsubscribe(Consumer<ServerSockets> consumer) {
        observer.unsubscribe(consumer);
    }

    @Override
    public void notifyObserver(ServerSockets serverSocket) {
        observer.consume(serverSocket);
    }
}
