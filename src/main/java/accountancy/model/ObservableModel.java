package accountancy.model;

import accountancy.observer.Observable;
import accountancy.observer.Observer;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 * Concrete implementation of the Observable interface
 */
public abstract class ObservableModel implements Observable {

    private transient ArrayList<Observer> listObserver          = null;
    private transient boolean             hasUnpublishedChanges = false;
    private transient boolean             onTransaction         = false;

    @Override public void addObserver(Observer observer) {

        if (!this.listObserver().contains(observer)) {
            this.listObserver().add(observer);
        }
    }

    private ArrayList<Observer> listObserver() {

        if (listObserver == null) {
            listObserver = new ArrayList<>();
        }
        return listObserver;
    }

    @Override public void removeObserver(Observer observer) {

        if (this.listObserver().contains(observer)) {
            this.listObserver().remove(observer);
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
                for (Observer obs : this.listObserver()) {
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
