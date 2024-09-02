package CommandMapper;

import AppBuilder.Interfaces.ICommandMapper;
import Command.*;
import CommandParser.*;
import CommandParser.Interfaces.CommandParser;
import Consumer.Consumer;
import Consumer.StringConsumer;
import Observer.ObserverObservableMiddleware;
import Producer.CommandProducer;
import Producer.StringProducer;

public class CommandMapper implements StringConsumer, CommandProducer, ICommandMapper {

    private final ObserverObservableMiddleware<Command> observer;
    private final CommandParser[] commandParsers = {
            new CommandListenParser(),
            new CommandDisplayServersParser(),
            new CommandOpenParser(),
            new CommandDisplayClientsParser(),
            new CommandConnectedParser(),
            new CommandSendParser(),
            new CommandDisconnectParser(),
            new CommandSendToSocketParser()
    };

    public CommandMapper(StringProducer... stringProducers) {
        this.observer = new ObserverObservableMiddleware<>();
        for (StringProducer producer : stringProducers) {
            producer.subscribe(this);
        }
    }


    @Override
    public void consume(String s) {
        this.mapStringToCommand(s);
    }

    @Override
    public void subscribe(Consumer<Command> consumer) {
        this.observer.subscribe(consumer);
    }

    @Override
    public void unsubscribe(Consumer<Command> consumer) {
        this.observer.unsubscribe(consumer);
    }

    @Override
    public void notifyObserver(Command command) {
        observer.consume(command);
    }

    @Override
    public void mapStringToCommand(String string) {
        boolean commandFound = false;
        for (CommandParser parser : commandParsers) {
            Command command = parser.parse(string,null);
            if (command != null) {
                commandFound = true;
                notifyObserver(command);
                break;
            }
        }
        if (!commandFound) {
            System.out.println("Invalid command");
        }
    }
}
