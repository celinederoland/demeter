package accountancy.view.views.account;


import accountancy.framework.Observer;
import accountancy.model.Entity;
import accountancy.model.base.Currency;
import accountancy.repository.BaseRepository;
import accountancy.view.components.PCombo;

import javax.swing.*;

public class ComboCurrency extends PCombo implements Observer {

    private final BaseRepository repository;

    public ComboCurrency(BaseRepository repository) {

        this.repository = repository;
        for (Entity currency : repository.currencies().getAll()) {
            this.addItem(currency);
        }
    }

    public void update() {

        SwingUtilities.invokeLater(() -> {

            Currency previous = getValue();

            removeAllItems();
            for (Entity currency : repository.currencies().getAll()) {
                addItem(currency);
            }

            revalidate();
            repaint();

            if (previous.id() == 0) {
                setSelectedItem(repository.currencies().getOne(previous.title()));
                return;
            }

            setSelectedItem(previous);
        });

    }

    public Currency getValue() {

        if (this.getSelectedItem() != null && this.getSelectedItem().toString().equals(this.inputField.getText())) {
            return (Currency) this.getSelectedItem();
        }
        return new Currency(0, this.inputField.getText());
    }
}
