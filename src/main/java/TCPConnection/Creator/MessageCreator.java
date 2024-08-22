package Creator;

import Creator.Interfaces.MessageCreate;
import Message.Message;

import java.net.Socket;


public class MessageCreator implements MessageCreate {

    private final String message;

    public MessageCreator(String message) {
        this.message = message;
    }

    @Override
    public Message create() {
        return new Message(message);
    }
}
