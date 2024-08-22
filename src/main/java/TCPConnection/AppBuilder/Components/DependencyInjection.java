package AppBuilder.Components;

import InputReader.StdinReader;
import Managers.Actions.EnhancedSocketActions;
import Managers.Actions.ServerSocketActions;
import Managers.EnhancedSocketPoolManager;
import Managers.ServerSocketPoolManager;
import CommandMapper.CommandMapper;
import Producer.CommandProducer;

public class DependencyInjection {


    public static ServerSocketActions getServerSocketPoolManager() {
        return new ServerSocketPoolManager();
    }

    public static EnhancedSocketActions getEnhancedSocketPoolManager() {
        return new EnhancedSocketPoolManager();
    }

    public static CommandProducer getCommandMapper(StdinReader stdinReader) {
        return new CommandMapper(stdinReader);
    }


}
