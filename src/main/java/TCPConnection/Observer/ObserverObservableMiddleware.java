package Observer;

import Consumer.Consumer;
import Producer.Producer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ObserverObservableMiddleware<T> implements Consumer<T>, Producer<T> {

    private final List<Consumer<T>> consumers;


    public ObserverObservableMiddleware() {
        this.consumers = new CopyOnWriteArrayList<>();
    }

    @Override
    public void consume(T t) {
        for (Consumer<T> consumer : consumers) {
            consumer.consume(t);
        }
    }

    @Override
    public void subscribe(Consumer<T> consumer) {
        this.consumers.add(consumer);
    }

    @Override
    public void unsubscribe(Consumer<T> consumer) {
        this.consumers.remove(consumer);
    }

    @Override
    public void notifyObserver(T t) {
    }

}
