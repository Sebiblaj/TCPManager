package CommandParser;

import Command.*;
import CommandParser.Interfaces.CommandParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandOpenParser implements CommandParser {

    private final Pattern pattern_bind = Pattern.compile(
            "^open\\s+(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}|localhost)\\s" +
                    "(\\d{1,5})\\s+(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}|localhost)\\s+(\\d{1,5})" +
                    "(?:\\s+(\\S+))?$");

    private final Pattern pattern_auto_bind = Pattern.compile(
            "^open\\s+((?:\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}|localhost)?)\\s+(\\d{1,5})(?:\\s+(\\S+))?$");

    @Override
    public Command parse(String stringCommand,Object o) {
        Matcher matcher = pattern_bind.matcher(stringCommand);
        if (matcher.matches()) {
            String localIP = matcher.group(1);
            int localPort = Integer.parseInt(matcher.group(2));
            String remoteIP = matcher.group(3);
            int remotePort = Integer.parseInt(matcher.group(4));
            String name = matcher.group(5) != null ? matcher.group(5) : "";
            return new CommandOpen(localIP, localPort, remoteIP, remotePort, name);
        } else {
            matcher = pattern_auto_bind.matcher(stringCommand);
            if (matcher.matches()) {
                String remoteIP = matcher.group(1).isEmpty() ? "localhost" : matcher.group(1);
                int remotePort = Integer.parseInt(matcher.group(2));
                String name = matcher.group(3) != null ? matcher.group(3) : "";
                return new CommandConnect(remoteIP, remotePort, name);
            }
        }
        return null;
    }
}
