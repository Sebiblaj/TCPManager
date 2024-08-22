package CommandParser;

import Command.*;
import CommandParser.Interfaces.CommandParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandDisconnectParser implements CommandParser {

    private final Pattern pattern = Pattern.compile("^disconnect\\s+(client|server)\\s+((\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})|localhost)\\s+(\\d{1,5})$");

    @Override
    public Command parse(String stringCommand) {
        Matcher matcher = pattern.matcher(stringCommand);
        if (matcher.matches()) {
            String type = matcher.group(1);
            String ip = matcher.group(2);
            int port = Integer.parseInt(matcher.group(4));
            if (type.equals("client")) {
                return new CommandDisconnect(ip, port);
            } else if (type.equals("server")) {
                return new CommandStop(ip, port);
            }
        }
        return null;
    }
}
