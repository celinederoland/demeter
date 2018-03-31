package accountancy.framework;

/**
 * An implementation of the pattern Observer/Observable
 * When notified of changes, the observer must be able to update himself.
 */
public interface Observer {

    /**
     * When notified of changes, the observer must be able to update himself.
     */
    void update();
}
