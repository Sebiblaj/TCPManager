package Wrapper;

import java.net.InetAddress;
import java.net.Socket;

public class SocketEnhanced {

    private final Socket socket;
    private final InetAddress inetAddress;
    private final int port;
    private final String name;

    public SocketEnhanced(Socket socket, InetAddress address, int port, String name) {
        this.socket = socket;
        this.inetAddress = address;
        this.port = port;
        this.name = name;
    }

    public Socket getSocket() {
        return socket;
    }
    public InetAddress getInetAddress() { return inetAddress;}
    public int getPort() { return port; }
    public String getName() { return name; }

}
