package Managers;

import Managers.Actions.SocketActions;
import Wrapper.SocketEnhanced;

import java.net.InetAddress;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class SocketPoolManager implements SocketActions {

    private final List<SocketEnhanced> clientSockets;

    public SocketPoolManager() {
        this.clientSockets = Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public void add(SocketEnhanced socket) {
        clientSockets.add(socket);
    }

    @Override
    public void remove(InetAddress ip, int port) {
        for(SocketEnhanced socket : clientSockets) {
            if(socket.getInetAddress().equals(ip) && socket.getPort() == port) {
                clientSockets.remove(socket);
                System.out.println("ClientSocket removed from pool: " + socket);
            }
        }
    }

    public void removeAll(){
        for(SocketEnhanced socket : clientSockets) {
            clientSockets.remove(socket);
        }
    }

    @Override
    public void display(InetAddress inetAddress, int port) {
        if (!clientSockets.isEmpty()) {
            for (SocketEnhanced socket : clientSockets) {
                    System.out.println(socket.getSocket());
            }
        } else {
            System.out.println("No client sockets in the pool.");
        }
    }

    public List<SocketEnhanced> getAllSockets() {
        return new ArrayList<>(clientSockets);
    }

}
