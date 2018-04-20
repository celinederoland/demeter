package accountancy.view.views.account;

import accountancy.model.Entity;
import accountancy.model.base.Type;
import accountancy.observer.Observer;
import accountancy.repository.BaseRepository;
import accountancy.view.components.PCombo;

import javax.swing.*;

public class ComboType extends PCombo implements Observer {


    private final BaseRepository repository;

    public ComboType(BaseRepository repository) {

        this.repository = repository;
        for (Entity type : repository.types().getAll()) {
            this.addItem(type);
        }
    }

    public void update() {

        SwingUtilities.invokeLater(() -> {

            Type previous = getValue();

            removeAllItems();
            for (Entity type : repository.types().getAll()) {
                addItem(type);
            }

            revalidate();
            repaint();

            if (previous.id() == 0) {
                setSelectedItem(repository.types().getOne(previous.title()));
                return;
            }

            setSelectedItem(previous);
        });

    }

    public Type getValue() {

        if (this.getSelectedItem() != null && this.getSelectedItem().toString().equals(this.inputField.getText())) {
            return (Type) this.getSelectedItem();
        }
        return new Type(this.inputField.getText());
    }

}
