package Managers.Actions;

import Wrapper.SocketEnhanced;

import java.util.List;

public interface EnhancedSocketActions extends Actions<SocketEnhanced> {
    List<SocketEnhanced> getSocketEnhancedList();

    SocketEnhanced getSocketEnhanced(String ip, int port);
}
