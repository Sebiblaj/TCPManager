package CommandMapper.DisplayStuff;

import Command.*;
import Consumer.CommandConsumer;
import Managers.Actions.ServerSocketActions;
import Producer.CommandProducer;

public class DisplayServersMapper implements CommandConsumer {

    private final ServerSocketActions serverSocketPoolManager;

    public DisplayServersMapper(ServerSocketActions socketPoolManager, CommandProducer... commandProducers) {
        for (CommandProducer producer : commandProducers) {
            producer.subscribe(this);
        }
        this.serverSocketPoolManager = socketPoolManager;
    }

    @Override
    public void consume(Command command) {
        if(command instanceof CommandDisplayServers){
            serverSocketPoolManager.display(null,0);
        }
    }
}
