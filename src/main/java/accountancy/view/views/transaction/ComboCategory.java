package accountancy.view.views.transaction;

import accountancy.model.Entity;
import accountancy.model.base.Category;
import accountancy.observer.Observer;
import accountancy.repository.BaseRepository;
import accountancy.view.components.PAlert;
import accountancy.view.components.PCombo;

import javax.swing.*;

public class ComboCategory extends PCombo implements Observer {

    private final BaseRepository repository;

    public ComboCategory(BaseRepository repository) {

        this.repository = repository;

        repository.categories().addObserver(this);
        for (Entity category : repository.categories().getAll()) {
            ((Category) category).addObserver(this);
            this.addItem(category);
        }
    }

    public void update() {

        SwingUtilities.invokeLater(() -> {

            Category previous = getValue();

            removeAllItems();
            for (Entity category : repository.categories().getAll()) {
                addItem(category);
            }

            revalidate();
            repaint();

            if (previous.id() == 0) {
                setSelectedItem(repository.categories().getOne(previous.title()));
                return;
            }

            setSelectedItem(previous);
        });

    }

    public Category getValue() {

        if (this.getSelectedItem() != null && this.getSelectedItem().toString().equals(this.inputField.getText())) {
            return (Category) this.getSelectedItem();
        }
        return (Category) repository.categories().getOne();
    }

    @Override public void setSelectedItem(Object anObject) {

        if (((Category) anObject).subCategories().isEmpty()) {
            new PAlert(
                "impossible", "Cette catégorie ne peut pas être sélectionnée car elle n'a pas de sous catégories");
            return;
        }
        super.setSelectedItem(anObject);
    }

}
