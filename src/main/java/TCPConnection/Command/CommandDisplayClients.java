package Command;

public class CommandDisplayClients extends Command {

    public CommandDisplayClients() {
        super(CommandType.displayClients);
    }

    @Override
    public String toString(){
        return "Display Clients";
    }
}
