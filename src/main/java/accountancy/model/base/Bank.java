package accountancy.model.base;

import accountancy.model.ObservableEntity;

/**
 * Represent a bank establishment
 */
public final class Bank extends ObservableEntity {

    public Bank(int id) {

        super(id);
    }

    public Bank(String title) {

        super(title);
    }

    public Bank(int id, String title) {

        super(id, title);
    }
}
