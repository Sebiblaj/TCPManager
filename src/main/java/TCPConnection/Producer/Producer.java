package Producer;

import Consumer.Consumer;

public interface Producer<T> {

    void subscribe(Consumer<T> consumer);
    void unsubscribe(Consumer<T> consumer);
    void notifyObserver(T t);
}
