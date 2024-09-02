package Message;

import java.net.Socket;

public class SocketMessage extends Message{

    private Socket socket;

    public SocketMessage(String message, Socket socket) {
        super(message);
        this.socket = socket;
    }

    public SocketMessage(){
        super("");
        this.socket=null;
    }


    public void setSocket(Socket socket){ this.socket=socket;}

    public Socket getSocket() { return socket; }
}
