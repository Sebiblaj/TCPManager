package Creator;

import Creator.Interfaces.ServerSocketCreate;
import Wrapper.ServerSockets;
import java.net.InetAddress;
import java.net.ServerSocket;


public class ServerSocketCreator implements ServerSocketCreate {

    private final String ipAddress;
    private final int port;
    private final String name;


    public ServerSocketCreator(String ipAddress, int port,String name) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.name = name;
    }

    @Override
    public ServerSockets create() {
        try {
            InetAddress inetAddress = InetAddress.getByName(ipAddress);
            ServerSocket serverSocket = new ServerSocket(port, 0, inetAddress);
            ServerSockets serverSockets = new ServerSockets(serverSocket,name);
            System.out.println("Server started on IP " + inetAddress.getHostAddress() + " and port " + port+" with name " +name);
            return serverSockets;
        } catch (Exception e) {
            System.out.println("Could not start the server. " + e.getMessage());
        }
        return null;
    }
}
