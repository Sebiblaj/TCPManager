package Command;

public class Command {

    CommandType commandType;

    public Command(CommandType commandType){ this.commandType = commandType; }

    public CommandType getCommandType() { return commandType; }

}
