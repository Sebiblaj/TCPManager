package AppBuilder.Components;


import Consumer.CommandConsumer;
import Producer.CommandProducer;
import Command.*;

public class CommandLogger implements CommandConsumer {

    public CommandLogger(CommandProducer... producers)
    {
        for(CommandProducer producer: producers)
        {
            producer.subscribe(this);
        }
    }

    @Override
    public void consume(Command entity) {
        System.out.println("Consume command: "+ entity.getClass()
                +" of type: "+ entity.getCommandType());
        if(entity instanceof CommandListen commandListen)
        {
            System.out.println("Listen command detected: "+commandListen);
        }
    }
}

