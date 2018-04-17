package accountancy.view.views.category;

import accountancy.repository.BaseRepository;
import accountancy.view.components.PPanel;
import accountancy.view.config.Dimensions;

import java.awt.*;

public class CategoriesOuterPanel extends PPanel {

    public CategoriesOuterPanel(BaseRepository repository) {

        super(10, 10, 20, 10);
        this.setMinimumSize(Dimensions.EAST);

        this.setLayout(new BorderLayout());
        this.add(new CategoriesInnerPanel(repository), BorderLayout.CENTER);
    }
}
