package CommandMapper.DisconnectStuff;

import Command.*;
import Consumer.CommandConsumer;
import Consumer.Consumer;
import Disconnectors.DisconnectServer;
import Observer.ObserverObservableMiddleware;
import Producer.CommandProducer;
import Producer.DisconnectServerProducer;

public class DisconnectServerMapper implements CommandConsumer, DisconnectServerProducer {

    private final ObserverObservableMiddleware<DisconnectServer> observer;


    public DisconnectServerMapper(CommandProducer... commandProducers){
        for(CommandProducer commandProducer : commandProducers){
            commandProducer.subscribe(this);
        }
        this.observer = new ObserverObservableMiddleware<>();
    }

    @Override
    public void consume(Command command) {
        DisconnectServer disconnectServer=null;
         if(command instanceof CommandStop commandStop) {
             disconnectServer=new DisconnectServer(commandStop.getLocalPort(),commandStop.getLocalIPAddress());
         }
         if(disconnectServer!=null){
             notifyObserver(disconnectServer);
         }
    }

    @Override
    public void subscribe(Consumer<DisconnectServer> consumer) {
        observer.subscribe(consumer);
    }

    @Override
    public void unsubscribe(Consumer<DisconnectServer> consumer) {
       observer.unsubscribe(consumer);
    }

    @Override
    public void notifyObserver(DisconnectServer disconnectServer) {
        observer.consume(disconnectServer);
    }
}
