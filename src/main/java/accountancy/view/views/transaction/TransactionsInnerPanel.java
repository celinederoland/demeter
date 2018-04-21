package accountancy.view.views.transaction;

import accountancy.model.Entity;
import accountancy.model.base.Account;
import accountancy.model.base.Category;
import accountancy.model.base.SubCategory;
import accountancy.model.base.Transaction;
import accountancy.observer.Observer;
import accountancy.repository.BaseRepository;
import accountancy.view.components.PLoader;
import accountancy.view.components.PPanel;
import accountancy.view.components.PTitle;
import accountancy.view.config.Dimensions;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class TransactionsInnerPanel extends PPanel implements Observer {

    private final BaseRepository repository;

    public TransactionsInnerPanel(BaseRepository repository) {

        this.repository = repository;
        this.repository.transactions().addObserver(this);
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
                PPanel panelEntry = new TransactionPanel((Transaction) transaction, repository);
                last = panelEntry;
                add(panelEntry);
            }

            Category    oneCategory    = (Category) repository.categories().getOne();
            SubCategory oneSubCategory = null;
            if (oneCategory != null) {
                oneSubCategory = (SubCategory) oneCategory.subCategories().getOne();
                if (oneSubCategory == null)
                    oneSubCategory = repository.create(new SubCategory(oneCategory.title()), oneCategory);
            }
            Account oneAccount = (Account) repository.accounts().getOne();

            PPanel panelEntry = new TransactionPanel(
                new Transaction(
                    "", 0,
                    new Date(),
                    oneAccount,
                    oneCategory,
                    oneSubCategory
                ), repository);
            add(panelEntry);

            if (last != null) last.requestFocus();

            revalidate();
            repaint();
            loader.dispose();
        });
    }


}
