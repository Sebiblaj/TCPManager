package Command;


public class CommandOpen extends Command {

    private final String localIPAddress;
    private final int localPort;
    private final String remoteIPAddress;
    private final int remotePort;
    private final String name;

    public CommandOpen(String localIPAddress, int localPort, String remoteIPAddress, int remotePort, String name) {
        super(CommandType.open);
        this.localIPAddress = localIPAddress;
        this.localPort = localPort;
        this.remoteIPAddress = remoteIPAddress;
        this.remotePort = remotePort;
        this.name = name;
    }

    public String getLocalIPAddress() {return localIPAddress;}
    public int getLocalPort() {return localPort;}
    public String getRemoteIPAddress() {return remoteIPAddress;}
    public int getRemotePort() {return remotePort;}
    public String getName() {return name;}


    @Override
    public String toString() {
        return "CommandOpen between "
                +getLocalIPAddress()+" on port "+getLocalPort()
                + " and "+getRemoteIPAddress()+" on port "+getRemotePort();
    }

}
