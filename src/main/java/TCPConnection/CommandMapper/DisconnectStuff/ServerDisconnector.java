package CommandMapper.DisconnectStuff;

import Consumer.CommandConsumer;
import Consumer.DisconnectServerConsumer;
import Disconnectors.DisconnectServer;
import Executors.Utilities.Handlers.HandleDisconnections;
import Managers.Actions.ServerSocketActions;
import Producer.DisconnectServerProducer;

public class ServerDisconnector implements DisconnectServerConsumer {

    private final ServerSocketActions serverSocketPoolManager;
    private final CommandConsumer acceptConnections;

    public ServerDisconnector(ServerSocketActions serverSocketPoolManager,
                              CommandConsumer acceptConnections, DisconnectServerProducer... disconnectServerProducers) {
        this.serverSocketPoolManager = serverSocketPoolManager;
        this.acceptConnections = acceptConnections;

        for (DisconnectServerProducer disconnectServerProducer : disconnectServerProducers) {
            disconnectServerProducer.subscribe(this);
        }
    }

    @Override
    public void consume(DisconnectServer disconnectServer) {
        HandleDisconnections.disconnectServer(serverSocketPoolManager, disconnectServer, acceptConnections);
    }
}
