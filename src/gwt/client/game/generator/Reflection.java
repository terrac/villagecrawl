package gwt.client.game.generator;
public interface Reflection {
    public <T> T instantiate( Class<T> clazz );
}