package accountancy.view.views.entry;

import accountancy.framework.Observer;
import accountancy.model.Entity;
import accountancy.model.base.Account;
import accountancy.model.base.Category;
import accountancy.model.base.SubCategory;
import accountancy.model.base.Transaction;
import accountancy.repository.BaseRepository;
import accountancy.view.components.PLoader;
import accountancy.view.components.PPanel;
import accountancy.view.components.PTitle;
import accountancy.view.config.Dimensions;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class EntriesInnerPanel extends PPanel implements Observer {

    private final BaseRepository repository;

    public EntriesInnerPanel(BaseRepository repository) {

        this.repository = repository;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        update();
    }

    @Override public void update() {

        SwingUtilities.invokeLater(() -> {

            PLoader loader = new PLoader("Loading ...");
            removeAll();

            PTitle title = new PTitle("Entrées");
            title.setFixedSize(Dimensions.CENTER_WIDTH);
            add(title);
            add(Box.createRigidArea(new Dimension(0, 10)));


            PPanel last = null;
            for (Entity transaction : repository.transactions().getAll()) {
                PPanel panelEntry = new EntryPanel((Transaction) transaction, repository);
                last = panelEntry;
                add(panelEntry);
            }

            PPanel panelEntry = new EntryPanel(
                new Transaction(
                    0, "", 0,
                    new Date(),
                    (Account) repository.accounts().getOne(),
                    (Category) repository.categories().getOne(),
                    (SubCategory) ((Category) repository.categories().getOne()).subCategories().getOne()
                ), repository);
            add(panelEntry);

            if (last != null) last.requestFocus();

            revalidate();
            repaint();
            loader.dispose();
        });
    }


}
