package accountancy.view.views.category;

import accountancy.framework.Observer;
import accountancy.model.Entity;
import accountancy.model.base.Category;
import accountancy.model.base.SubCategory;
import accountancy.repository.BaseRepository;
import accountancy.view.components.AbstractMultiListener;
import accountancy.view.components.PPanel;
import accountancy.view.components.PTextField;
import accountancy.view.config.Dimensions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;

public class CategoryPanel extends PPanel implements Observer {

    private final BaseRepository repository;
    private       Category       category;
    private       PTextField     titleField;
    private       PPanel         subCategoriesPanel;

    public CategoryPanel(Category category, BaseRepository repository) {

        super(0, 0, 0, 10);

        this.repository = repository;

        this.category = category;
        category.addObserver(this);

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setMinimumSize(Dimensions.EAST_CATEGORY);

        PPanel line1 = new PPanel();
        line1.setMaximumSize(Dimensions.EAST_CATEGORY_NAME);
        line1.setLayout(new BorderLayout());

        titleField = new PTextField();
        titleField.setMinimumSize(Dimensions.EAST_CATEGORY_NAME_WIDTH);
        titleField.setPreferredSize(Dimensions.EAST_CATEGORY_NAME);
        TitleListener titleListener = new TitleListener();
        titleField.addActionListener(titleListener);
        titleField.addFocusListener(titleListener);
        line1.add(titleField, BorderLayout.CENTER);

        this.add(line1);

        subCategoriesPanel = new PPanel();
        subCategoriesPanel.setLayout(new BoxLayout(subCategoriesPanel, BoxLayout.PAGE_AXIS));

        this.add(subCategoriesPanel);
        this.update();
    }

    @Override public void update() {

        SwingUtilities.invokeLater(() -> {

            titleField.setText(category.title());

            subCategoriesPanel.removeAll();

            for (Entity subCategory : category.subCategories().getAll()) {
                SubCategoryPanel panelSubCategory = new SubCategoryPanel(
                    category, (SubCategory) subCategory, repository);
                subCategoriesPanel.add(panelSubCategory);
            }

            SubCategoryPanel panelSubCategory = new SubCategoryPanel(category, new SubCategory(0, ""), repository);
            subCategoriesPanel.add(panelSubCategory);

            revalidate();
            repaint();
        });
    }

    private void save() {

        if (category.id() > 0) {
            repository.save(category);
        }
        else if (!category.title().isEmpty()) {
            category = repository.create(category);
        }
    }

    class TitleListener extends AbstractMultiListener {


        public void actionPerformed(ActionEvent e) {

            this.save();
        }

        private void save() {

            (new UpdateTitleModel()).start();
        }

        public void focusLost(FocusEvent e) {

            this.save();
        }
    }

    class UpdateTitleModel extends Thread {

        public void run() {

            category.title(titleField.getText());
            save();
        }
    }
}
