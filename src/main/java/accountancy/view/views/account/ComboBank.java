package accountancy.view.views.account;


import accountancy.model.Entity;
import accountancy.model.base.Bank;
import accountancy.observer.Observer;
import accountancy.repository.BaseRepository;
import accountancy.view.components.PCombo;

import javax.swing.*;

public class ComboBank extends PCombo implements Observer {


    private final BaseRepository repository;

    public ComboBank(BaseRepository repository) {

        this.repository = repository;

        for (Entity bank : repository.banks().getAll()) {
            this.addItem(bank);
        }
    }

    public void update() {

        SwingUtilities.invokeLater(() -> {

            Bank previous = getValue();

            removeAllItems();
            for (Entity bank : repository.banks().getAll()) {
                addItem(bank);
            }

            revalidate();
            repaint();

            if (previous.id() == 0) {
                setSelectedItem(repository.banks().getOne(previous.title()));
                return;
            }

            setSelectedItem(previous);
        });

    }

    public Bank getValue() {

        if (this.getSelectedItem() != null && this.getSelectedItem().toString().equals(this.inputField.getText())) {
            return (Bank) this.getSelectedItem();
        }
        return new Bank(this.inputField.getText());
    }
}
