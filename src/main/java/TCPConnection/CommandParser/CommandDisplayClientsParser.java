package CommandParser;

import Command.*;
import CommandParser.Interfaces.CommandParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandDisplayClientsParser implements CommandParser {

    private final Pattern pattern=Pattern.compile(
            "^display");

    @Override
    public Command parse(String stringCommand) {
        Matcher matcher=pattern.matcher(stringCommand);
        if(matcher.matches()){
            return new CommandDisplayClients();
        }
        return null;
    }
}
