package CommandMapper.DisconnectStuff;

import Command.*;
import CommandMapper.InterfacesFactory.DisconnectClientFactory;
import Consumer.CommandConsumer;
import Consumer.Consumer;
import Creator.Interfaces.DisconnectorClientCreate;
import Disconnectors.DisconnectClient;
import Observer.ObserverObservableMiddleware;
import Producer.CommandProducer;
import Producer.DisconnectClientProducer;

public class DisconnectClientMapper implements CommandConsumer, DisconnectClientProducer {

    private final ObserverObservableMiddleware<DisconnectClient> observer;
    private final DisconnectClientFactory disconnectClientFactory;

    public DisconnectClientMapper(DisconnectClientFactory disconnectClientFactory,CommandProducer... producers) {
        for(CommandProducer producer : producers) {
            producer.subscribe(this);
        }
        observer = new ObserverObservableMiddleware<>();
        this.disconnectClientFactory = disconnectClientFactory;
    }
    @Override
    public void consume(Command command) {
        DisconnectClient disconnectClient=null;
        if(command instanceof CommandDisconnect commandDisconnect){
            DisconnectorClientCreate disconnectorClientCreate=disconnectClientFactory.make(new DisconnectClient(commandDisconnect.getRemotePort(),commandDisconnect.getRemoteIPAddress()));
            disconnectClient = disconnectorClientCreate.create();
        }
        if(disconnectClient!=null){
            notifyObserver(disconnectClient);
        }
    }

    @Override
    public void subscribe(Consumer<DisconnectClient> consumer) {
       observer.subscribe(consumer);
    }

    @Override
    public void unsubscribe(Consumer<DisconnectClient> consumer) {
        observer.unsubscribe(consumer);
    }

    @Override
    public void notifyObserver(DisconnectClient disconnectClient) {
        observer.consume(disconnectClient);
    }
}
