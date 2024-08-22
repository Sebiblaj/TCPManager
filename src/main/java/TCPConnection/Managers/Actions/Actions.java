package Managers.Actions;

import java.net.InetAddress;

public interface Actions<T> {

    void add(T t);
    void remove(InetAddress ip,int port);
    void display(InetAddress ip,int port);
}
