package Message;

import java.net.Socket;

public class SocketMessage extends Message{

    private final Socket socket;

    public SocketMessage(String message, Socket socket) {
        super(message);
        this.socket = socket;
    }

    public Socket getSocket() { return socket; }
}
