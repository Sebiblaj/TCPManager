package Command;

public class CommandListen extends Command {

    private String localIPAddress;
    private final int localPort;
    private final String name;

    public CommandListen(String localIPAddress, int localPort, String name) {
        super(CommandType.listen);
        this.localIPAddress = localIPAddress;
        this.localPort = localPort;
        this.name = name;
    }

    public CommandListen(int localPort, String name) {
        super(CommandType.listen);
        this.localPort = localPort;
        this.name = name;
    }

    public String getLocalIPAddress() {
        return localIPAddress;
    }

    public int getLocalPort() {
        return localPort;
    }

    public String getName() { return name; }

    @Override
    public String toString() {
        return "CommandListen on IP " + getLocalIPAddress() + " and port " + getLocalPort();
    }
}
