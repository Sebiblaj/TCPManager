package CommandMapper.DisconnectStuff;

import Consumer.DisconnectClientConsumer;
import Disconnectors.DisconnectClient;
import Executors.Utilities.Handlers.HandleDisconnections;
import Managers.Actions.EnhancedSocketActions;
import Producer.DisconnectClientProducer;


public class ClientDisconnector implements DisconnectClientConsumer {

    private final EnhancedSocketActions enhancedSocketPoolManager;

    public ClientDisconnector(EnhancedSocketActions enhancedSocketPoolManager, DisconnectClientProducer... clientProducers) {
        for (DisconnectClientProducer clientProducer : clientProducers) {
            clientProducer.subscribe(this);
        }
       this.enhancedSocketPoolManager=enhancedSocketPoolManager;
    }

    @Override
    public void consume(DisconnectClient disconnectClient) {
        HandleDisconnections.disconnectClient(enhancedSocketPoolManager, disconnectClient);
    }
}
