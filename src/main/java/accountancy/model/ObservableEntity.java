package accountancy.model;

import accountancy.framework.Observer;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public abstract class ObservableEntity extends Entity {

    protected boolean             hasUnpublishedChanges = false;
    private   boolean             onTransaction         = false;
    private   ArrayList<Observer> listObserver          = new ArrayList<>();

    public ObservableEntity(int id, String title) {

        super(id, title);
    }

    @Override public void addObserver(Observer obs) {

        if (!this.listObserver.contains(obs)) {
            this.listObserver.add(obs);
        }
    }

    @Override public void removeObserver() {

        listObserver = new ArrayList<>();
    }

    @Override public void startTransaction() {

        this.onTransaction = true;
    }

    @Override public void commit() {

        this.onTransaction = false;
        if (this.hasUnpublishedChanges) this.publish();
    }

    @Override public void publish() {

        if(!this.onTransaction) {
            try {
                this.hasUnpublishedChanges = false;
                for (Observer obs : this.listObserver) {
                    obs.update();
                }
            } catch (ConcurrentModificationException e) {
                e.printStackTrace();
            }
        } else {
            this.hasUnpublishedChanges = true;
        }
    }

}
