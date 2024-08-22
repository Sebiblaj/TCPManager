package CommandParser;

import Command.*;
import CommandParser.Interfaces.CommandParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandDisplayServersParser implements CommandParser {

    private static final Pattern pattern_ip_port = Pattern.compile(
            "^display\\s+servers$"
    );


    @Override
    public Command parse(String stringCommand) {
        Matcher matcher = pattern_ip_port.matcher(stringCommand);
        if (matcher.matches()) {
            return new CommandDisplayServers();
        }
        return null;
    }

}
