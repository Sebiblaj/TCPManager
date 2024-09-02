package InputReader;

import Observer.ObserverObservableMiddleware;
import Producer.StringProducer;
import Consumer.Consumer;

import java.util.Scanner;

public class StdinReader implements StringProducer, Runnable {

    private final ObserverObservableMiddleware<String> observer;
    private volatile boolean running = true;

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
        try (Scanner scanner = new Scanner(System.in)) {
            while (running && scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.equals("close")) {
                    running = false;
                    System.out.println("Closing connection");
                } else {
                    notifyObserver(line);
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading input: " + e.getMessage());
        } finally {
            System.out.println("Thread exited");
        }
    }
}
