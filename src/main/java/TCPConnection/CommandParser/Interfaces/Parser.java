package CommandParser.Interfaces;

public interface Parser<T,S> {
    T parse(S s);
}
