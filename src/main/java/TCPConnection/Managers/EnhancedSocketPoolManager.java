package Managers;

import Managers.Actions.EnhancedSocketActions;
import Wrapper.SocketEnhanced;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class EnhancedSocketPoolManager implements EnhancedSocketActions {

    private final List<SocketEnhanced> socketEnhancedList;

    public EnhancedSocketPoolManager() {
        socketEnhancedList = new ArrayList<>();
    }

    @Override
    public void add(SocketEnhanced socketEnhanced) {
         socketEnhancedList.add(socketEnhanced);
         System.out.println("EnhancedSocketPoolManager: Adding " + socketEnhanced);
    }

    @Override
    public synchronized void remove(InetAddress ip, int port) {
        try{
        for(SocketEnhanced socketEnhanced : socketEnhancedList) {
            if(socketEnhanced.getInetAddress()==ip && socketEnhanced.getPort()==port) {
                socketEnhancedList.remove(socketEnhanced);
                System.out.println("EnhancedSocketPoolManager: Removing " + socketEnhanced);
            }
        }
        }catch(ConcurrentModificationException e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void display(InetAddress ip,int port) {
           if(!socketEnhancedList.isEmpty()) {
               for(SocketEnhanced socketEnhanced : socketEnhancedList) {
                   System.out.println(socketEnhanced.getSocket());
               }
           }else{
               System.out.println("EnhancedSocketPoolManager: No socket enhanced");
           }
    }

    public List<SocketEnhanced> getSocketEnhancedList() {
        return socketEnhancedList;
    }


    public SocketEnhanced getSocketEnhanced(String ip, int port){
        InetAddress inetAddress = null;
        try{
            inetAddress=InetAddress.getByName(ip);
        }catch(Exception e){
            System.out.println("Could not transform into InetAddress");
        }
        if(inetAddress!=null) {
            for (SocketEnhanced socketEnhanced : socketEnhancedList) {
                if(socketEnhanced.getInetAddress()==inetAddress && socketEnhanced.getPort() == port) {
                    return socketEnhanced;
                }
            }
        }
        return null;
    }
}
