package CommandMapper.ClientStuff;


import Consumer.SocketEnhancedConsumer;
import Executors.Utilities.Handlers.HandleSockets;
import Managers.Actions.EnhancedSocketActions;
import Producer.SocketEnhancedProducer;
import Wrapper.SocketEnhanced;


public class ClientSocketsListener implements SocketEnhancedConsumer {

    private final EnhancedSocketActions enhancedSocketPoolManager;


    public ClientSocketsListener(EnhancedSocketActions enhancedSocketPoolManager, SocketEnhancedProducer... socketProducers) {
        for(SocketEnhancedProducer socketProducer : socketProducers) {
            socketProducer.subscribe(this);
        }
        this.enhancedSocketPoolManager=enhancedSocketPoolManager;

    }
    @Override
    public void consume(SocketEnhanced socketEnhanced) {
        HandleSockets.handleClientSockets(socketEnhanced,enhancedSocketPoolManager);
    }


}
