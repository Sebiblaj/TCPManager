package AppBuilder.Components;

import CommandMapper.ClientStuff.ClientSocketMapper;
import CommandMapper.ClientStuff.ClientSocketsListener;
import CommandMapper.InterfacesFactory.ServerSocketFactory;
import CommandMapper.InterfacesFactory.SocketFactory;
import CommandMapper.ServerStuff.ServerConnections;
import CommandMapper.ServerStuff.ServerSocketMapper;
import CommandParser.Interfaces.MessageWrapperParser;
import Consumer.MessageConsumer;
import Executors.Connectors.AcceptConnections;
import Managers.Actions.EnhancedSocketActions;
import Managers.Actions.ServerSocketActions;
import Producer.CommandProducer;


public class Connector {

    public Connector(MessageConsumer messageConsumer,ServerSocketFactory serverSocketFactory, SocketFactory socketFactory, CommandProducer commandMapper, ServerSocketActions serverSocketActions,
                     EnhancedSocketActions enhancedSocketActions, AcceptConnections acceptConnections, MessageWrapperParser[] parsers){

        ServerSocketMapper serverSocketMapper = new ServerSocketMapper(serverSocketFactory,commandMapper);
        new ServerConnections(messageConsumer,parsers,serverSocketActions,acceptConnections, serverSocketMapper);


        ClientSocketMapper clientSocketMapper = new ClientSocketMapper(messageConsumer,socketFactory,parsers,enhancedSocketActions,commandMapper);
        new ClientSocketsListener(enhancedSocketActions, clientSocketMapper);
    }

}
