package Creator;

import Creator.Interfaces.DisconnectorClientCreate;
import Disconnectors.DisconnectClient;

public class DisconnectorClientCreator implements DisconnectorClientCreate {

    private final int remotePort;
    private final String remoteIP;

    public DisconnectorClientCreator(DisconnectClient disconnectClient) {
        this.remotePort = disconnectClient.port();
        this.remoteIP = disconnectClient.ip();
    }

    @Override
    public DisconnectClient create() {
        return new DisconnectClient(remotePort,remoteIP);
    }
}
