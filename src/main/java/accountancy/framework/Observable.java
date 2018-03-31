package accountancy.framework;

/**
 * An implementation of the pattern Observer/Observable
 * We find the traditional methods addObserver and removeObserver
 * The method publish will notify all observers that a change happened
 *
 * We introduce a variation with the startTransaction method, which allow us to stop the notifications during the transaction.
 * The aim is to avoid useless multiple consecutive updates of observers.
 */
public interface Observable {


    /**
     * The new observer will be notified of any change in the observable instance.
     *
     * @param observer ...
     */
    void addObserver(Observer observer);

    /**
     * The observer won't be notified of changes anymore
     */
    void removeObserver(Observer observer);


    /**
     * During the transaction, the changes won't be communicated to observers.
     * The transaction finishes with a call to method commit
     */
    void startTransaction();

    /**
     * The end of the transaction, we can publish changes and notify observers.
     */
    void commit();


    /**
     * Notify observers
     */
    void publish();

}
