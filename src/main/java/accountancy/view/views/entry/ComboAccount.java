package accountancy.view.views.entry;

import accountancy.model.Entity;
import accountancy.model.base.Account;
import accountancy.observer.Observer;
import accountancy.repository.BaseRepository;
import accountancy.view.components.PCombo;

import javax.swing.*;

public class ComboAccount extends PCombo implements Observer {

    private final BaseRepository repository;

    public ComboAccount(BaseRepository repository) {

        this.repository = repository;
        repository.accounts().addObserver(this);
        for (Entity account : repository.accounts().getAll()) {
            ((Account) account).addObserver(this);
            this.addItem(account);
        }
    }

    public void update() {

        SwingUtilities.invokeLater(() -> {

            Account previous = getValue();

            removeAllItems();
            for (Entity account : repository.accounts().getAll()) {
                addItem(account);
            }

            revalidate();
            repaint();

            if (previous.id() == 0) {
                setSelectedItem(repository.accounts().getOne(previous.title()));
                return;
            }

            setSelectedItem(previous);
        });

    }

    public Account getValue() {

        if (this.getSelectedItem() != null && this.getSelectedItem().toString().equals(this.inputField.getText())) {
            return (Account) this.getSelectedItem();
        }
        return (Account) repository.accounts().getOne();
    }

}
