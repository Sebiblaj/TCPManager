package AppBuilder.Components;

import CommandMapper.ClassesFactory.ConcreteMessageFactory;
import CommandMapper.InterfacesFactory.MessageFactory;
import CommandMapper.MessageStuff.MessageMapper;
import Managers.Actions.EnhancedSocketActions;
import Managers.Actions.ServerSocketActions;
import Producer.CommandProducer;

public class Messager {

    public Messager(CommandProducer[] commandMapper, EnhancedSocketActions enhancedSocketActions, ServerSocketActions serverSocketActions){
        MessageFactory messageFactory=new ConcreteMessageFactory();
        new MessageMapper(serverSocketActions,enhancedSocketActions,messageFactory,commandMapper);
    }
}
