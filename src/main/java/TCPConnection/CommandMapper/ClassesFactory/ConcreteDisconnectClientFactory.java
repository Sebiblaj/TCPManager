package CommandMapper.ClassesFactory;

import CommandMapper.InterfacesFactory.DisconnectClientFactory;
import Creator.DisconnectorClientCreator;
import Creator.Interfaces.DisconnectorClientCreate;
import Disconnectors.DisconnectClient;

public class ConcreteDisconnectClientFactory implements DisconnectClientFactory {

    @Override
    public DisconnectorClientCreate make(Object... obj) {
        if (obj.length == 1 && obj[0] instanceof DisconnectClient) {
            return new DisconnectorClientCreator((DisconnectClient) obj[0]);
        }
        throw new IllegalArgumentException("Invalid argument type or number of arguments");
    }
}
