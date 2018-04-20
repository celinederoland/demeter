package accountancy.model.base;

import accountancy.model.ObservableEntity;

/**
 * Represent a subcategory
 */
public final class SubCategory extends ObservableEntity {

    public SubCategory(int id) {

        super(id);
    }

    public SubCategory(String title) {

        super(title);
    }

    public SubCategory(int id, String title) {

        super(id, title);
    }
}
