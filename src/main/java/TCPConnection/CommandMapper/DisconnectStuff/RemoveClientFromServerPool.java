package CommandMapper.DisconnectStuff;

import Consumer.MessageConsumer;
import Executors.Sender.Interfaces.MessageReceiver;
import Managers.Actions.ServerSocketActions;
import Message.*;
import Producer.MessageProducer;

public class RemoveClientFromServerPool implements MessageConsumer {

    private final ServerSocketActions serverSocketActions;
    private final MessageReceiver messageReceiver;

    public RemoveClientFromServerPool(MessageReceiver messageReceiver,ServerSocketActions serverSocketActions, MessageProducer... producers) {
        for (MessageProducer producer : producers) {
            producer.subscribe(this);
        }
        this.serverSocketActions = serverSocketActions;
        this.messageReceiver = messageReceiver;
    }
    @Override
    public void consume(Message messageWrapper) {
        if(messageWrapper.getMessage().equals("Client disconnected")&& messageWrapper instanceof SocketMessage socketMessage){
            serverSocketActions.removeClient(socketMessage.getSocket());
            System.out.println("Client removed : "+socketMessage.getSocket().getInetAddress()+
                    " port : "+socketMessage.getSocket().getPort());
            messageReceiver.stop();
        }
    }
}
