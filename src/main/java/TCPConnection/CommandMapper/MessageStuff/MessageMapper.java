package CommandMapper.MessageStuff;

import Command.*;
import Consumer.CommandConsumer;
import CommandMapper.InterfacesFactory.MessageFactory;
import Creator.Interfaces.MessageCreate;
import Executors.Utilities.Handlers.HandleMessages;
import Managers.Actions.EnhancedSocketActions;
import Managers.Actions.ServerSocketActions;
import Message.Message;
import Producer.CommandProducer;


public class MessageMapper implements CommandConsumer {

    private final MessageFactory messageFactory;
    private final EnhancedSocketActions enhancedSocketActions;
    private final ServerSocketActions serverSocketActions;

    public MessageMapper(ServerSocketActions serverSocketActions,
                         EnhancedSocketActions enhancedSocketActions,MessageFactory messageFactory, CommandProducer... commandProducers) {
        this.messageFactory = messageFactory;
        for (CommandProducer commandProducer : commandProducers) {
            commandProducer.subscribe(this);
        }
        this.enhancedSocketActions = enhancedSocketActions;
        this.serverSocketActions = serverSocketActions;
    }


    @Override
    public void consume(Command command) {
        Message message = null;
        if (command instanceof CommandSend commandSend) {
            MessageCreate messageCreator = messageFactory.make(commandSend.getMessage());
            message = messageCreator.create();
        }
        if (message != null) {
            HandleMessages.handleMessages(serverSocketActions,enhancedSocketActions,message);
        }
    }

}
