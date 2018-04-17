package accountancy.view.views.category;

import accountancy.model.base.Category;
import accountancy.model.base.SubCategory;
import accountancy.observer.Observer;
import accountancy.repository.BaseRepository;
import accountancy.view.components.AbstractMultiListener;
import accountancy.view.components.PPanel;
import accountancy.view.components.PTextField;
import accountancy.view.config.Dimensions;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;

public class SubCategoryPanel extends PPanel implements Observer {

    private final BaseRepository repository;
    private       SubCategory    subCategory;
    private       Category       category;
    private       PTextField     titleField;

    public SubCategoryPanel(Category category, SubCategory subCategory, BaseRepository repository) {

        super(10, 0, 0, 0);

        this.repository = repository;

        this.category = category;
        this.subCategory = subCategory;
        subCategory.addObserver(this);

        this.setFixedSize(Dimensions.EAST_SUB_CATEGORY);
        this.setLayout(new BorderLayout());

        this.titleField = new PTextField();
        TitleListener titleListener = new TitleListener();
        titleField.addActionListener(titleListener);
        titleField.addFocusListener(titleListener);
        this.add(this.titleField, BorderLayout.CENTER);

        this.update();
    }


    @Override public void update() {

        titleField.setText(subCategory.title());
    }

    private void save() {

        if (subCategory.id() > 0) {
            repository.save(subCategory);
        }
        else if (!subCategory.title().isEmpty()) {
            subCategory = repository.create(subCategory, category);
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

            subCategory.title(titleField.getText());
            save();
        }
    }
}
