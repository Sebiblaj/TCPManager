package CommandParser;

import Command.*;
import CommandParser.Interfaces.CommandParser;

import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandSendToSocketParser implements CommandParser {

    private final Pattern pattern = Pattern.compile("^send\\s+(.*)$");

    @Override
    public Command parse(String s, Object object) {
        Matcher matcher = pattern.matcher(s);
        if (matcher.matches()) {
            String message=matcher.group(1);
            if(object instanceof Socket socket)
              return new CommandSendWithSocket(socket,message);
        }
        return null;
    }
}
