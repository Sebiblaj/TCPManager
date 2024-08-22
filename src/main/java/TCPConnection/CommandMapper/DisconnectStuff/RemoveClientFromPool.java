package CommandMapper.DisconnectStuff;

import Consumer.MessageConsumer;
import Executors.Sender.Interfaces.MessageReceiver;
import Managers.Actions.EnhancedSocketActions;
import Message.Message;
import Producer.MessageProducer;

import java.net.InetAddress;

public class RemoveClientFromPool implements MessageConsumer {

    private final EnhancedSocketActions enhancedSocketActions;
    private final InetAddress inetAddress;
    private final int port;
    private final MessageReceiver receive;

    public RemoveClientFromPool(MessageReceiver receive, InetAddress inetAddress, int port, EnhancedSocketActions enhancedSocketActions, MessageProducer... producers) {
        for (MessageProducer producer : producers) {
            producer.subscribe(this);
        }
        this.enhancedSocketActions = enhancedSocketActions;
        this.inetAddress = inetAddress;
        this.port = port;
        this.receive = receive;
    }

    @Override
    public void consume(Message messageWrapperProducer) {
        if(messageWrapperProducer.getMessage().equals("Server disconnected")) {
            enhancedSocketActions.remove(inetAddress, port);
            System.out.println("Removed " + inetAddress + " from pool");
            receive.stop();
        }
    }
}
