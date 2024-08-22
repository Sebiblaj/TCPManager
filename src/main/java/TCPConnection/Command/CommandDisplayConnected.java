package Command;

public class CommandDisplayConnected extends Command {

    private final int port;
    private final String ip;

    public CommandDisplayConnected(String ip, int port) {
        super(CommandType.displayConnected);
        this.ip = ip;
        this.port = port;
    }

    public int getPort() { return port; }
    public String getIp() { return ip; }

    @Override
    public String toString(){
        return "Display Connected";
    }
}
