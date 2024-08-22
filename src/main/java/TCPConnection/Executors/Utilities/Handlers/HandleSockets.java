package Executors.Utilities.Handlers;

import Command.DummyCommands.CommandAcceptConnections;
import CommandMapper.ServerStuff.SocketsAfterConnection;
import CommandParser.Interfaces.MessageWrapperParser;
import Consumer.MessageConsumer;
import Executors.Connectors.AcceptConnections;
import Managers.Actions.EnhancedSocketActions;
import Managers.Actions.ServerSocketActions;
import Wrapper.ServerSockets;
import Wrapper.SocketEnhanced;

public class HandleSockets {

    public static void handleServerSockets(MessageConsumer messageConsumer,MessageWrapperParser[]parsers,
                                           ServerSockets serverSocket, ServerSocketActions serverSocketPoolManager, AcceptConnections acceptConnections) {
        serverSocketPoolManager.add(serverSocket.socket());
        acceptConnections.consume(new CommandAcceptConnections(serverSocket.socket(),serverSocket.name()));
        new SocketsAfterConnection(messageConsumer,serverSocket.socket(),serverSocketPoolManager,parsers,acceptConnections);
    }

    public static void handleClientSockets(SocketEnhanced socketEnhanced, EnhancedSocketActions enhancedSocketPoolManager){
        enhancedSocketPoolManager.add(socketEnhanced);
    }

}
