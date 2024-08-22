package Command.DummyCommands;

import Command.*;
import java.net.ServerSocket;

public class CommandAcceptConnections extends Command {

    private final ServerSocket serverSocket;
    private final String name;

    public CommandAcceptConnections(ServerSocket serverSocket,String name) {
        super(CommandType.acceptConnections);
        this.serverSocket = serverSocket;
        this.name = name;
    }

    public ServerSocket getServerSocket() { return serverSocket; }
    public String getName() { return name; }

}
