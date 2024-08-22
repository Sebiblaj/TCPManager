package CommandParser.Interfaces;

import Command.Command;

public interface CommandParser extends Parser<Command,String> {

    Command parse(String stringCommand);
}
