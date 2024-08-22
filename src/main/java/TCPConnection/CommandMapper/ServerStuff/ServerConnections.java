package CommandMapper.ServerStuff;


import CommandParser.Interfaces.MessageWrapperParser;
import Consumer.MessageConsumer;
import Consumer.ServerSocketConsumer;
import Executors.Connectors.AcceptConnections;
import Executors.Utilities.Handlers.HandleSockets;
import Managers.Actions.ServerSocketActions;
import Producer.ServerSocketProducer;
import Wrapper.ServerSockets;



public class ServerConnections implements ServerSocketConsumer {

    private final ServerSocketActions serverSocketPoolManager;
    private final AcceptConnections acceptConnections;
    private final MessageWrapperParser[] messageWrapperParser;
    private final MessageConsumer messageConsumer;

    public ServerConnections(MessageConsumer messageConsumer,MessageWrapperParser[] parsers,
                             ServerSocketActions serverSocketPoolManager, AcceptConnections acceptConnections, ServerSocketProducer... producers) {
        this.serverSocketPoolManager = serverSocketPoolManager;
        for (ServerSocketProducer producer : producers) {
            producer.subscribe(this);
        }
        this.acceptConnections = acceptConnections;
        this.messageWrapperParser = parsers;
        this.messageConsumer = messageConsumer;
    }

    @Override
    public void consume(ServerSockets serverSocket) {
        HandleSockets.handleServerSockets(messageConsumer,messageWrapperParser,serverSocket,serverSocketPoolManager,acceptConnections);
    }

}
