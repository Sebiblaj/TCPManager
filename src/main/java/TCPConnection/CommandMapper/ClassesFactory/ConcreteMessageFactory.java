package CommandMapper.ClassesFactory;

import CommandMapper.InterfacesFactory.MessageFactory;
import Creator.Interfaces.MessageCreate;
import Creator.MessageCreator;



public class ConcreteMessageFactory implements MessageFactory {

    @Override
    public MessageCreate make(Object... obj) {
        if (obj.length==1 && obj[0] instanceof String) {
            return new MessageCreator((String) obj[0]);
        }
        throw new IllegalArgumentException("Invalid argument type");
    }
}
