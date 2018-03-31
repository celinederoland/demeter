package accountancy.model.base;

import accountancy.model.ObservableEntity;

/**
 * Represent a currency (eg. USD, EUR ...)
 */
public final class Currency extends ObservableEntity {

    public Currency(int id, String title) {

        super(id, title);
    }
}
