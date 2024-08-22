package Command;

public class CommandDisplayServers extends Command{

    public CommandDisplayServers() {
        super(CommandType.displayServers);
    }

    @Override
    public String toString(){
        return "DisplayServers";
    }

}
