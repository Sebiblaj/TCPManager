package Managers.Actions;

import Managers.SocketPoolManager;
import Wrapper.SocketEnhanced;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public interface ServerSocketActions extends Actions<ServerSocket> {
    void addClient(InetAddress inetAddress, int port, Socket socket,String name);

    void displayClientSockets(int port, String ip);

    List<SocketEnhanced> getAllSockets();

    SocketPoolManager getSocketPool(InetAddress inetAddress, int port);

    void removeClients(InetAddress inetAddress, int port);

    void removeClient(Socket socket);
}
