package AppBuilder.Components;

import CommandMapper.DisplayStuff.DisplayClientsMapper;
import CommandMapper.DisplayStuff.DisplayConnectedMapper;
import CommandMapper.DisplayStuff.DisplayServersMapper;
import Managers.Actions.EnhancedSocketActions;
import Managers.Actions.ServerSocketActions;
import Producer.CommandProducer;

public class Displayer {

    public Displayer(CommandProducer commandMapper, ServerSocketActions serverSocketActions, EnhancedSocketActions enhancedSocketActions){
        new DisplayServersMapper(serverSocketActions,commandMapper);
        new DisplayClientsMapper(enhancedSocketActions,commandMapper);
        new DisplayConnectedMapper(serverSocketActions,commandMapper);
    }
}
