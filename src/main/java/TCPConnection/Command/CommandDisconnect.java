package Command;

public class CommandDisconnect extends Command {

    private final String remoteIPAddress;
    private final int remotePort;

    public CommandDisconnect(String remoteIPAddress, int remotePort) {
        super(CommandType.disconnect);
        this.remoteIPAddress = remoteIPAddress;
        this.remotePort = remotePort;
    }

    public String getRemoteIPAddress() {return remoteIPAddress;}
    public int getRemotePort() {return remotePort;}

    @Override
    public String toString() {
        return "CommandDisconnect on IP" +getRemoteIPAddress()+ " and port "+getRemotePort();
    }
}
