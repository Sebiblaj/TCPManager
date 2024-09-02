package Command;

import java.net.Socket;

public class CommandSendWithSocket extends CommandSend{

    private final Socket socket;

    public CommandSendWithSocket(Socket socket,String message) {
        super(message);
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }
}
