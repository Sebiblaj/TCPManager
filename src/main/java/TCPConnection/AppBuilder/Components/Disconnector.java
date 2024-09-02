package AppBuilder.Components;

import CommandMapper.ClassesFactory.ConcreteDisconnectClientFactory;
import CommandMapper.DisconnectStuff.ClientDisconnector;
import CommandMapper.DisconnectStuff.DisconnectClientMapper;
import CommandMapper.DisconnectStuff.DisconnectServerMapper;
import CommandMapper.DisconnectStuff.ServerDisconnector;
import CommandMapper.InterfacesFactory.DisconnectClientFactory;
import Consumer.CommandConsumer;
import Managers.Actions.EnhancedSocketActions;
import Managers.Actions.ServerSocketActions;
import Producer.CommandProducer;


public class Disconnector {

    public Disconnector(CommandProducer[] commandMapper, EnhancedSocketActions enhancedSocketActions,
                        ServerSocketActions serverSocketActions, CommandConsumer acceptConnections) {

        DisconnectClientFactory clientFactory = new ConcreteDisconnectClientFactory();
        DisconnectClientMapper disconnectClientMapper = new DisconnectClientMapper(clientFactory,commandMapper);
        new ClientDisconnector(enhancedSocketActions, disconnectClientMapper);

        DisconnectServerMapper disconnectServerMapper = new DisconnectServerMapper(commandMapper);
        new ServerDisconnector(serverSocketActions, acceptConnections, disconnectServerMapper);
    }
}
