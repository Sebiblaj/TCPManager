package Managers;

import Managers.Actions.ServerSocketActions;
import Managers.Actions.SocketActions;
import Wrapper.SocketEnhanced;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ServerSocketPoolManager implements ServerSocketActions {

    private final Map<ServerSocket, SocketPoolManager> socketPool;

    public ServerSocketPoolManager() {
        this.socketPool = new HashMap<>();
    }

    @Override
    public void add(ServerSocket socket) {
        socketPool.putIfAbsent(socket, new SocketPoolManager());
        System.out.println("serverSocket added to pool: " + socket);
    }

    @Override
    public void addClient(InetAddress inetAddress, int port, Socket socket,String name) {
        for (Map.Entry<ServerSocket, SocketPoolManager> entry : socketPool.entrySet()) {
            if (entry.getKey().getInetAddress().equals(inetAddress) && entry.getKey().getLocalPort() == port) {
                entry.getValue().add(new SocketEnhanced(socket,inetAddress,port,name));
            }
        }
    }

    @Override
    public void remove(InetAddress ip, int port) {
        Iterator<Map.Entry<ServerSocket, SocketPoolManager>> iterator = socketPool.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<ServerSocket, SocketPoolManager> entry = iterator.next();
            if (entry.getKey().getInetAddress().equals(ip) && entry.getKey().getLocalPort() == port) {
                iterator.remove();
                System.out.println("serverSocket removed from pool: " + entry.getKey());
            }
        }
    }

    @Override
    public void removeClient(Socket socket) {
        SocketActions socketActions;
        for (Map.Entry<ServerSocket, SocketPoolManager> entry : socketPool.entrySet()) {
            socketActions=entry.getValue();
            socketActions.remove(socket.getInetAddress(),socket.getLocalPort());

        }
    }

    public void removeClients(InetAddress ip, int port) {
        for (ServerSocket socket : socketPool.keySet()) {
            if (socket.getInetAddress().equals(ip) && socket.getLocalPort() == port) {
                SocketPoolManager socketPoolManager = socketPool.get(socket);
                socketPoolManager.removeAll();
                System.out.println("Clients removed for serverSocket: " + socket);
            }
        }
    }


    @Override
    public void display(InetAddress ip, int port) {
        if (socketPool.isEmpty()) {
            System.out.println("No sockets in the pool.");
        } else {
            for (ServerSocket socket : socketPool.keySet()) {
                System.out.println(socket);
            }
        }
    }


    public void displayClientSockets(int port, String ip) {
        for (ServerSocket socket : socketPool.keySet()) {
            try {
                if (socket.getInetAddress().equals(InetAddress.getByName(ip)) && socket.getLocalPort() == port) {
                    SocketPoolManager socketPoolManager = socketPool.get(socket);
                    System.out.println("Client sockets for server socket: " + socket);
                    socketPoolManager.display(null, 0);
                }
            } catch (Exception e) {
                System.out.println("Could not get socket from pool: " + socket + " due to error: " + e.getMessage());
            }
        }
    }

    public List<SocketEnhanced> getAllSockets() {
        List<SocketEnhanced> allSockets = new ArrayList<>();
        for (SocketPoolManager manager : socketPool.values()) {
            allSockets.addAll(manager.getAllSockets());
        }
        return allSockets;
    }

    public SocketPoolManager getSocketPool(InetAddress ip, int port) {
        for (ServerSocket socket : socketPool.keySet()) {
            if (socket.getInetAddress().equals(ip) && socket.getLocalPort() == port) {
                return socketPool.get(socket);
            }
        }
        return null;
    }
}
