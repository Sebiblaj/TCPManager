package Consumer;

@FunctionalInterface
public interface Consumer<T> {
    void consume(T t);
}
