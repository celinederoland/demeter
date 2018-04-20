package accountancy.view.views.account;

import accountancy.model.Entity;
import accountancy.model.base.*;
import accountancy.observer.Observer;
import accountancy.repository.BaseRepository;
import accountancy.view.components.PPanel;
import accountancy.view.components.PTitle;
import accountancy.view.config.Dimensions;

import javax.swing.*;
import java.awt.*;

public class AccountsInnerPanel extends PPanel implements Observer {

    private final BaseRepository repository;

    public AccountsInnerPanel(BaseRepository repository) {

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.repository = repository;
        update();
    }

    @Override public void update() {

        SwingUtilities.invokeLater(() -> {

            removeAll();

            PTitle title = new PTitle("Comptes");
            title.setFixedSize(Dimensions.WEST_WIDTH);
            add(title);

            add(Box.createRigidArea(new Dimension(0, 10)));

            Accounts accounts = repository.accounts();

            PPanel last = null;
            for (Entity account : accounts.getAll()) {
                PPanel panelAccount = new AccountPanel((Account) account, repository);
                last = panelAccount;
                add(panelAccount);
            }

            PPanel panelAccount = new AccountPanel(
                new Account(
                    "",
                    (Currency) repository.currencies().getOne(),
                    (Bank) repository.banks().getOne(),
                    (Type) repository.types().getOne()
                ), repository);
            add(panelAccount);

            revalidate();
            repaint();
            if (last != null) last.requestFocus();
            else panelAccount.requestFocus();
        });
    }
}
