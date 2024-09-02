package CommandParser;

import Command.*;
import CommandParser.Interfaces.CommandParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandConnectedParser implements CommandParser {

    private final Pattern pattern = Pattern.compile(
            "^display\\s+connected\\s+((\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})|localhost)\\s+(\\d{1,5})$");

    @Override
    public Command parse(String stringCommand,Object o) {
        Matcher matcher = pattern.matcher(stringCommand);
        if (matcher.matches()) {
            String host = matcher.group(1);
            int port = Integer.parseInt(matcher.group(3));
            return new CommandDisplayConnected(host, port);
        }
        return null;
    }

}
