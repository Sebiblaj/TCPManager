package CommandParser;

import Command.Command;
import Command.CommandListen;
import CommandParser.Interfaces.CommandParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandListenParser implements CommandParser {

    private static final Pattern pattern_ip_port = Pattern.compile(
            "^listen\\s+(\\d+\\.\\d+\\.\\d+\\.\\d+)\\s+(\\d+)(?:\\s+(\\S+))?$"
    );

    private static final Pattern pattern_port = Pattern.compile(
            "^listen\\s+(\\d+)(?:\\s+(\\S+))?$"
    );

    @Override
    public Command parse(String stringCommand) {
        Matcher matcher = pattern_ip_port.matcher(stringCommand);
        if (matcher.matches()) {
            String ip = matcher.group(1);
            int port = Integer.parseInt(matcher.group(2));
            String name = matcher.group(3) != null ? matcher.group(3) : "";
            return new CommandListen(ip, port, name);
        } else {
            matcher = pattern_port.matcher(stringCommand);
            if (matcher.matches()) {
                int port = Integer.parseInt(matcher.group(1));
                String name = matcher.group(2) != null ? matcher.group(2) : "";
                return new CommandListen(port, name);
            }
        }
        return null;
    }
}
