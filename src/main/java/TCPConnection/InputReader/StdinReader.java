package InputReader;

import Observer.ObserverObservableMiddleware;
import Producer.StringProducer;

import java.util.Scanner;
import Consumer.Consumer;

public class StdinReader implements StringProducer,Runnable {

    private final ObserverObservableMiddleware<String> observer;

    public StdinReader() {
        this.observer = new ObserverObservableMiddleware<>();
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void subscribe(Consumer<String> consumer) {
         observer.subscribe(consumer);
    }

    @Override
    public void unsubscribe(Consumer<String> consumer) {
         observer.unsubscribe(consumer);
    }

    @Override
    public void notifyObserver(String s) {
        observer.consume(s);
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            notifyObserver(line);
        }

    }
}
