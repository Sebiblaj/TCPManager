package Creator;


import Creator.Interfaces.SocketCreate;

import java.net.InetAddress;
import java.net.Socket;

public class SocketCreator implements SocketCreate {

    private String localIP="localhost";
    private int localPort;
    private final String remoteIP;
    private final int remotePort;

    public SocketCreator(String localIP, int localPort, String remoteIP, int remotePort) {
        this.localIP = localIP;
        this.localPort = localPort;
        this.remoteIP = remoteIP;
        this.remotePort = remotePort;
    }

    public SocketCreator(String remoteIP, int remotePort) {
        this.remoteIP = remoteIP;
        this.remotePort = remotePort;
    }

    @Override
    public Socket create() {
        try {
            if (!localIP.equals("localhost")) {
                InetAddress inetAddress1 = InetAddress.getByName(localIP);
                InetAddress inetAddress2 = InetAddress.getByName(remoteIP);
                Socket socket = new Socket(inetAddress1, localPort, inetAddress2, remotePort);
                System.out.println("Client connected from IP " + inetAddress1.getHostAddress() + " and port " + localPort +
                        " to server at IP " + inetAddress2.getHostAddress() + " and port " + remotePort);
                return socket;
            } else {
                InetAddress inetAddress1 = InetAddress.getByName(remoteIP);
                Socket socket = new Socket(inetAddress1, remotePort);
                System.out.println("Client connected to server at IP " + remoteIP + " and port " + remotePort);
                return socket;
            }
        } catch (Exception e) {
            System.out.println("Could not start client connection: " + e.getMessage());
            return null;
        }
    }
}
