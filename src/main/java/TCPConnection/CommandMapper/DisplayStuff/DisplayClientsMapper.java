package CommandMapper.DisplayStuff;

import Command.*;
import Consumer.CommandConsumer;
import Managers.Actions.EnhancedSocketActions;
import Producer.CommandProducer;


public class DisplayClientsMapper implements CommandConsumer {

    private final EnhancedSocketActions enhancedSocketPoolManager;

    public DisplayClientsMapper(EnhancedSocketActions enhancedSocketPoolManager, CommandProducer... commandProducers) {
        for(CommandProducer commandProducer : commandProducers) {
            commandProducer.subscribe(this);
        }
        this.enhancedSocketPoolManager=enhancedSocketPoolManager;
    }

    @Override
    public void consume(Command command) {
        if(command instanceof CommandDisplayClients){
            enhancedSocketPoolManager.display(null,0);
        }
    }
}
