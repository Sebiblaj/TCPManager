package CommandMapper.ClientStuff;


import Consumer.SocketEnhancedConsumer;
import Executors.Utilities.Handlers.HandleSockets;
import Managers.Actions.EnhancedSocketActions;
import Producer.SocketEnhancedProducer;
import Wrapper.SocketEnhanced;


public class ClientSocketsListener implements SocketEnhancedConsumer {

    private final EnhancedSocketActions enhancedSocketPoolManager;
    private final SocketEnhancedProducer[] socketEnhancedProducer;


    public ClientSocketsListener(EnhancedSocketActions enhancedSocketPoolManager, SocketEnhancedProducer... socketProducers) {
        for(SocketEnhancedProducer socketProducer : socketProducers) {
            socketProducer.subscribe(this);
        }
        this.enhancedSocketPoolManager=enhancedSocketPoolManager;
        this.socketEnhancedProducer=socketProducers;

    }


    @Override
    public void consume(SocketEnhanced socketEnhanced) {
        if(socketEnhanced==null) {
            for(SocketEnhancedProducer socketProducer : socketEnhancedProducer) {
                socketProducer.unsubscribe(this);
            }
        }else {
            HandleSockets.handleClientSockets(socketEnhanced, enhancedSocketPoolManager);
        }
    }


}
