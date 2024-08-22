package CommandMapper.InterfacesFactory;

public interface Factory<T> {
    T make(Object... obj);
}
