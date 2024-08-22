package Command;

public class CommandStop extends Command {

    private final String localIPAddress;
    private final int localPort;

    public CommandStop(String localIPAddress, int localPort) {
        super(CommandType.listen);
        this.localIPAddress = localIPAddress;
        this.localPort = localPort;
    }

    public String getLocalIPAddress() {
        return localIPAddress;
    }
    public int getLocalPort() {return localPort;}


    @Override
    public String toString() {
        return "CommandStop on IP " + getLocalIPAddress()
                + " and port " + getLocalPort();
    }
}
