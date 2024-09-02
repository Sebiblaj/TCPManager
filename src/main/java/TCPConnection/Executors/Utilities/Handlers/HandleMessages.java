package Executors.Utilities.Handlers;

import Executors.Sender.Interfaces.MessageSender;
import Executors.Sender.Send;
import Managers.Actions.EnhancedSocketActions;
import Managers.Actions.ServerSocketActions;
import Message.Message;
import Wrapper.SocketEnhanced;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HandleMessages {

    private static final Map<Socket, MessageSender> socketSendMap = new HashMap<>();

    public static void handleMessages(ServerSocketActions serverSocketPoolManager,
                                      EnhancedSocketActions enhancedSocketPoolManager, Message message) {
        sendMessageToSockets(serverSocketPoolManager.getAllSockets(), message);
        sendMessageToSockets(enhancedSocketPoolManager.getSocketEnhancedList(), message);
    }

    private static void sendMessageToSockets(Iterable<SocketEnhanced> sockets, Message message) {
        for (SocketEnhanced socketEnhanced : sockets) {
            if (socketEnhanced != null && !socketEnhanced.getSocket().isClosed()) {
                sendMessage(socketEnhanced.getSocket(), message);
            }
        }
    }

    private static void sendMessage(Socket socket, Message message) {
        MessageSender sender = socketSendMap.computeIfAbsent(socket, Send::new);
        try {
            sender.sendMessage(message);
        } catch (Exception e) {
            System.out.println("Error sending message to socket: " + e.getMessage());
        }
    }

    public static void sendDisconnectionMessage(SocketEnhanced socketEnhanced) {
        if (socketEnhanced != null && !socketEnhanced.getSocket().isClosed()) {
            sendMessage(socketEnhanced.getSocket(), new Message("A client has disconnected"));
        }
    }

    public static void sendStopMessage(Socket socket) {
        if (socket != null && !socket.isClosed()) {
            sendMessage(socket, new Message("The server has disconnected"));
            try {
                socket.close();
            } catch (Exception e) {
                System.out.println("Error closing socket: " + e.getMessage());
            }
        }
    }
}
