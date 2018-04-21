package accountancy.view.views.transaction;

import accountancy.model.Entity;
import accountancy.model.base.Category;
import accountancy.model.base.SubCategory;
import accountancy.observer.Observer;
import accountancy.view.components.PCombo;

import javax.swing.*;

public class ComboSubCategory extends PCombo implements Observer {

    Category category;

    public ComboSubCategory(Category category) {

        this.category = category;
        category.addObserver(this);
        this.update();
    }

    public void update() {

        SwingUtilities.invokeLater(() -> {

            SubCategory previous = getValue();

            removeAllItems();
            for (Entity subCategory : category.subCategories().getAll()) {
                ((SubCategory) subCategory).addObserver(ComboSubCategory.this);
                addItem(subCategory);
            }

            revalidate();
            repaint();

            if (previous != null && previous.id() == 0) {
                setSelectedItem(category.subCategories().getOne(previous.title()));
                return;
            }

            setSelectedItem(previous);
        });

    }

    public SubCategory getValue() {

        if (this.getSelectedItem() != null && this.getSelectedItem().toString().equals(this.inputField.getText())) {
            return (SubCategory) this.getSelectedItem();
        }
        return (SubCategory) category.subCategories().getOne();
    }

    public void setCategory(Category category) {

        if (this.category != category) {
            this.category = category;
            category.addObserver(this);
            this.update();
        }
    }

}
