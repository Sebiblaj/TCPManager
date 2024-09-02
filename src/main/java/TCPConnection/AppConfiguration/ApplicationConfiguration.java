package AppConfiguration;

import AppBuilder.Components.*;
import AppBuilder.Interfaces.Configure;
import CommandMapper.ClassesFactory.ConcreteServerSocketFactory;
import CommandMapper.ClassesFactory.ConcreteSocketFactory;
import CommandMapper.InterfacesFactory.ServerSocketFactory;
import CommandMapper.InterfacesFactory.SocketFactory;
import CommandParser.Interfaces.MessageWrapperParser;
import Executors.Connectors.AcceptConnections;
import InputReader.StdinReader;
import Managers.Actions.EnhancedSocketActions;
import Managers.Actions.ServerSocketActions;
import Parsers.JsonParser;
import Producer.CommandProducer;

public class ApplicationConfiguration implements Configure {

    public ServerSocketActions serverSocketPoolManager() {
        return DependencyInjection.getServerSocketPoolManager();
    }

    public EnhancedSocketActions enhancedSocketPoolManager() {
        return DependencyInjection.getEnhancedSocketPoolManager();
    }

    public CommandProducer commandMapper(StdinReader stdinReader) {
        return DependencyInjection.getCommandMapper(stdinReader);
    }

    @Override
    public void configure() {
        ServerSocketActions serverSocketActions = serverSocketPoolManager();
        EnhancedSocketActions enhancedSocketActions = enhancedSocketPoolManager();

        StdinReader stdinReader = new StdinReader();
        CommandProducer[] commandMapper = new CommandProducer[]{commandMapper(stdinReader)};
        SocketFactory socketFactory=new ConcreteSocketFactory();
        ServerSocketFactory serverSocketFactory=new ConcreteServerSocketFactory();
        AcceptConnections acceptConnections = new AcceptConnections(commandMapper);

        new Connector(null,null,serverSocketFactory,socketFactory,commandMapper, serverSocketActions, enhancedSocketActions,acceptConnections,new MessageWrapperParser[]{new JsonParser()});
        new Displayer(commandMapper, serverSocketActions, enhancedSocketActions);
        new Messager(commandMapper, enhancedSocketActions, serverSocketActions);
        new Disconnector(commandMapper, enhancedSocketActions, serverSocketActions,acceptConnections);

    }
}

