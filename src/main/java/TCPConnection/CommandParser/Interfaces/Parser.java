package CommandParser.Interfaces;


public interface Parser<T,S,U> {
    T parse(S s,U u);
}
