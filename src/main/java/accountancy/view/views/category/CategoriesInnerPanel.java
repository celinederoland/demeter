package accountancy.view.views.category;

import accountancy.framework.Observer;
import accountancy.model.Entity;
import accountancy.model.base.Category;
import accountancy.repository.BaseRepository;
import accountancy.view.components.PPanel;
import accountancy.view.components.PTitle;
import accountancy.view.config.Dimensions;

import javax.swing.*;
import java.awt.*;

public class CategoriesInnerPanel extends PPanel implements Observer {

    private final BaseRepository repository;

    public CategoriesInnerPanel(BaseRepository repository) {

        this.repository = repository;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        update();
    }

    @Override public void update() {

        SwingUtilities.invokeLater(() -> {

            removeAll();

            PTitle title = new PTitle("Catégories");
            title.setFixedSize(Dimensions.EAST_WIDTH);
            add(title);

            add(Box.createRigidArea(new Dimension(0, 10)));

            for (Entity category : repository.categories().getAll()) {
                PPanel panelCategory = new CategoryPanel((Category) category, repository);
                add(panelCategory);
            }

            PPanel panelCategory = new CategoryPanel(new Category(0, ""), repository);
            add(panelCategory);

            revalidate();
            repaint();
        });
    }


}
