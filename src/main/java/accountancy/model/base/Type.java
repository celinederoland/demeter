package accountancy.model.base;

import accountancy.model.ObservableEntity;

/**
 * Represent the aim of one bank account (eg current, save money, credit etc)
 */
public final class Type extends ObservableEntity {

    public Type(int id, String title) {

        super(id, title);
    }
}
