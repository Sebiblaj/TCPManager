package CommandMapper.ClassesFactory;

import Command.CommandConnect;
import Command.CommandOpen;
import Command.DummyCommands.CommandAcceptConnections;
import CommandMapper.InterfacesFactory.SocketFactory;
import Creator.Interfaces.SocketCreate;
import Creator.SocketCreator;

public class ConcreteSocketFactory implements SocketFactory {

    @Override
    public SocketCreate make(Object... obj) {
        if(obj.length==1 ){
            if(obj[0] instanceof CommandOpen commandOpen){
                return new SocketCreator(commandOpen.getLocalIPAddress(), commandOpen.getLocalPort(),
                        commandOpen.getRemoteIPAddress(), commandOpen.getRemotePort());
            }else if(obj[0] instanceof CommandConnect connect){
                return new SocketCreator(connect.getRemoteIPAddress(), connect.getRemotePort());
            }else if(obj[0] instanceof CommandAcceptConnections acceptConnections){
                return new SocketCreator(acceptConnections.getServerSocket().getInetAddress().toString(),
                        acceptConnections.getServerSocket().getLocalPort());
            }
        }
        throw new IllegalArgumentException("Invalid argument type");
    }
}
