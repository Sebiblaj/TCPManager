package Executors.Utilities.Handlers;

import Command.DummyCommands.CommandTerminate;
import Consumer.CommandConsumer;
import Disconnectors.DisconnectClient;
import Disconnectors.DisconnectServer;
import Managers.Actions.EnhancedSocketActions;
import Managers.Actions.ServerSocketActions;
import Managers.SocketPoolManager;
import Wrapper.SocketEnhanced;

import java.net.InetAddress;
import java.util.List;

public class HandleDisconnections {

    public static void disconnectClient(EnhancedSocketActions enhancedSocketPoolManager, DisconnectClient disconnectClient){
        try {
            SocketEnhanced socketEnhanced=enhancedSocketPoolManager.getSocketEnhanced(disconnectClient.ip(), disconnectClient.port());
            HandleMessages.sendDisconnectionMessage(socketEnhanced);
            socketEnhanced.getSocket().close();
            System.out.println("Disconnected from " + disconnectClient.ip() + ":" + disconnectClient.port());
        }catch(Exception e){
            System.out.println("Could not close the Client socket");
        }
    }

    public static void disconnectServer(ServerSocketActions serverSocketPoolManager, DisconnectServer disconnectServer,
                                        CommandConsumer acceptConnections) {
        try {
            InetAddress inetAddress = InetAddress.getByName(disconnectServer.ip());
            SocketPoolManager socketPoolManager = serverSocketPoolManager.getSocketPool(inetAddress, disconnectServer.port());

            if (socketPoolManager != null) {
                List<SocketEnhanced> sockets = socketPoolManager.getAllSockets();
                for (SocketEnhanced socket : sockets) {
                    HandleMessages.sendStopMessage(socket.getSocket());
                }
                serverSocketPoolManager.removeClients(inetAddress, disconnectServer.port());
                serverSocketPoolManager.remove(inetAddress, disconnectServer.port());
                acceptConnections.consume(new CommandTerminate());
            } else {
                System.out.println("No socket pool found for the given IP and port.");
            }
        } catch (Exception e) {
            System.out.println("Could not convert String to InetAddress: " + e.getMessage());
        }
    }



}
