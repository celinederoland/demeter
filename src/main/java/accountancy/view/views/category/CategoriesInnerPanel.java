package accountancy.view.views.category;

import accountancy.view.components.PPanel;
import accountancy.view.components.PTitle;
import accountancy.view.config.Dimensions;

import javax.swing.*;

public class CategoriesInnerPanel extends PPanel {

    public CategoriesInnerPanel() {

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        PTitle title = new PTitle("Catégories");
        title.setFixedSize(Dimensions.EAST_WIDTH);
        add(title);
    }

}
