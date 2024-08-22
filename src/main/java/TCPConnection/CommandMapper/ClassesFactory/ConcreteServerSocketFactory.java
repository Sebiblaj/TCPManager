package CommandMapper.ClassesFactory;

import Command.CommandListen;
import CommandMapper.InterfacesFactory.ServerSocketFactory;
import Creator.Interfaces.ServerSocketCreate;
import Creator.ServerSocketCreator;


public class ConcreteServerSocketFactory implements ServerSocketFactory {
    @Override
    public ServerSocketCreate make(Object... obj) {
        if (obj.length == 1 && obj[0] instanceof CommandListen commandListen) {
            return new ServerSocketCreator(commandListen.getLocalIPAddress(),commandListen.getLocalPort(), commandListen.getName());
        }
        throw new IllegalArgumentException("Invalid argument type or number of arguments");
    }
}
