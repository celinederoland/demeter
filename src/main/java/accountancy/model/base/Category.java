package accountancy.model.base;

import accountancy.model.ObservableEntity;
import accountancy.observer.Observer;

/**
 * Represent a category of expenditure
 * It must be subdivided into subcategories
 */
public final class Category extends ObservableEntity {

    private final SubCategories subCategories = new SubCategories();

    public Category(int id, String title) {

        super(id, title);
    }

    @Override public void addObserver(Observer observer) {

        super.addObserver(observer);
        subCategories.addObserver(observer);
    }

    public SubCategories subCategories() {

        return subCategories;
    }
}
