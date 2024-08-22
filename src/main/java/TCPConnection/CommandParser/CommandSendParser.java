package CommandParser;

import Command.Command;
import Command.CommandSend;
import CommandParser.Interfaces.CommandParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandSendParser implements CommandParser {

    private final Pattern pattern = Pattern.compile("^send\\s+(.*)$");

    @Override
    public Command parse(String stringCommand) {
        Matcher matcher = pattern.matcher(stringCommand);
        if (matcher.matches()) {
            String message=matcher.group(1);
            return new CommandSend(message);
        }
        return null;
    }
}
