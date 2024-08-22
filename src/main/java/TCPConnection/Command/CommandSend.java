package Command;

public class CommandSend extends Command{

    private final String message;

    public CommandSend(String message) {
        super(CommandType.send);
        this.message = message;
    }

    public String getMessage() { return message; }

    @Override
    public String toString() {
        return "CommandSend [message= " + message + "]";
    }
}
