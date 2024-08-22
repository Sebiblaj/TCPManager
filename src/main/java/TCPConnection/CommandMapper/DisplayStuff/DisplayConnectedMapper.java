package CommandMapper.DisplayStuff;

import Command.*;
import Consumer.CommandConsumer;
import Managers.Actions.ServerSocketActions;
import Producer.CommandProducer;

public class DisplayConnectedMapper implements CommandConsumer {

    private final ServerSocketActions serverSocketPoolManager;

    public DisplayConnectedMapper(ServerSocketActions serverSocketPoolManager, CommandProducer... commandProducers) {
        for(CommandProducer commandProducer : commandProducers) {
            commandProducer.subscribe(this);
        }
        this.serverSocketPoolManager = serverSocketPoolManager;
    }

    @Override
    public void consume(Command command) {
        if(command instanceof CommandDisplayConnected commandDisplayConnected) {
            serverSocketPoolManager.displayClientSockets(commandDisplayConnected.getPort(),commandDisplayConnected.getIp());
        }
    }
}
