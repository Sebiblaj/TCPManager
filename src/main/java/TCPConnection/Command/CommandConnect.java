package Command;

public class CommandConnect extends Command {

    private final String remoteIPAddress;
    private final int remotePort;
    private final String name;

    public CommandConnect(String remoteIPAddress, int remotePort,String name) {
        super(CommandType.connect);
        this.remoteIPAddress = remoteIPAddress;
        this.remotePort = remotePort;
        this.name = name;
    }

    public String getRemoteIPAddress() {return remoteIPAddress;}
    public int getRemotePort() {return remotePort;}
    public String getName() {return name;}



    @Override
    public String toString() {
        return "CommandConnect on IP" +getRemoteIPAddress()+ " and port "+getRemotePort();
    }
}
