package AppConfiguration;

import AppBuilder.Components.*;
import CommandMapper.ClassesFactory.ConcreteServerSocketFactory;
import CommandMapper.ClassesFactory.ConcreteSocketFactory;
import CommandMapper.InterfacesFactory.ServerSocketFactory;
import CommandMapper.InterfacesFactory.SocketFactory;
import CommandParser.Interfaces.MessageWrapperParser;
import Consumer.MessageConsumer;
import Executors.Connectors.AcceptConnections;
import Managers.Actions.EnhancedSocketActions;
import Managers.Actions.ServerSocketActions;
import Managers.EnhancedSocketPoolManager;
import Managers.ServerSocketPoolManager;
import Producer.CommandProducer;

public class ProxyConfiguration {

    private final MessageConsumer  messageConsumer;
    private final CommandProducer commandProducers;
    private final MessageWrapperParser[] messageWrapperParser;

    public ProxyConfiguration(CommandProducer commandProducers, MessageConsumer messageConsumer, MessageWrapperParser[] messageWrapperParsers) {
        this.messageConsumer=messageConsumer;
        this.commandProducers = commandProducers;
        this.messageWrapperParser = messageWrapperParsers;
    }

    public void setupCommunication() {

        SocketFactory socketFactory=new ConcreteSocketFactory();
        ServerSocketFactory serverSocketFactory=new ConcreteServerSocketFactory();

        new CommandLogger(commandProducers);

        ServerSocketActions serverSocketActions = new ServerSocketPoolManager();
        EnhancedSocketActions enhancedSocketActions = new EnhancedSocketPoolManager();

        AcceptConnections acceptConnections=new AcceptConnections(commandProducers);

        new Connector(messageConsumer,serverSocketFactory,socketFactory,commandProducers,serverSocketActions,enhancedSocketActions,acceptConnections,messageWrapperParser);
        new Displayer(commandProducers,serverSocketActions,enhancedSocketActions);
        new Messager(commandProducers,enhancedSocketActions,serverSocketActions);
        new Disconnector(commandProducers,enhancedSocketActions,serverSocketActions,acceptConnections);
    }
}
