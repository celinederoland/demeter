package accountancy.model.base;

import accountancy.model.ObservableEntity;

/**
 * Represent a category of expenditure
 * It must be subdivided into subcategories
 */
public final class Category extends ObservableEntity {

    private SubCategories subCategories = new SubCategories();

    public Category(int id, String title) {

        super(id, title);
    }

    public SubCategories subCategories() {

        return subCategories;
    }
}
