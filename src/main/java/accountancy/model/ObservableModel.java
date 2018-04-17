package accountancy.model;

import accountancy.observer.Observable;
import accountancy.observer.Observer;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 * Concrete implementation of the Observable interface
 */
public abstract class ObservableModel implements Observable {

    private       boolean             hasUnpublishedChanges = false;
    private       boolean             onTransaction         = false;
    private final ArrayList<Observer> listObserver          = new ArrayList<>();

    @Override public void addObserver(Observer observer) {

        if (!this.listObserver.contains(observer)) {
            this.listObserver.add(observer);
        }
    }

    @Override public void removeObserver(Observer observer) {

        if (this.listObserver.contains(observer)) {
            this.listObserver.remove(observer);
        }
    }

    @Override public void startTransaction() {

        this.onTransaction = true;
    }

    @Override public void commit() {

        this.onTransaction = false;
        if (this.hasUnpublishedChanges) this.publish();
    }

    @Override public void publish() {

        if (!this.onTransaction) {
            try {
                this.hasUnpublishedChanges = false;
                for (Observer obs : this.listObserver) {
                    obs.update();
                }
            } catch (ConcurrentModificationException e) {
                e.printStackTrace();
            }
        }
        else {
            this.hasUnpublishedChanges = true;
        }
    }
}
